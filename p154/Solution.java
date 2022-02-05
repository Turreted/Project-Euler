import java.util.*;
import java.io.*;
import java.lang.IndexOutOfBoundsException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Solution {

    private int depth;

    private ArrayList<Integer> primes;
    private int[] fivesLookup;
    private int[] twosLookup;

    public Solution(int depth) {
        this.depth = depth;

        // compute primes less then depth
        System.out.println("Generating Primes under " + depth);
        this.primes = listPrimes(depth);

        System.out.println("Generating Prime Factorial Factorization lookup table for 2..");
        this.twosLookup = genPrimeFactorLookup(depth, 2);
        System.out.println("Generating Prime Factorial Factorization lookup table for 5..");
        this.fivesLookup = genPrimeFactorLookup(depth, 5);


        //System.out.println(this.primeFactors);
    }

    // compute the factorial of a number
    public BigInteger factorial(int num) {
        BigInteger factorial = BigInteger.ONE;

        for (int i = num; i > 0; i--) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }

        return factorial;
    }

    public BigInteger computeTrinomial(int n, int x, int z, int y) {
        BigInteger bn = factorial(n);
        BigInteger bx = factorial(x);
        BigInteger bz = factorial(y);
        BigInteger by = factorial(z);

        return bn.divide(bx.multiply(bz).multiply(by));
    }

    // list all primes less than N using the sieve of eratosthenes algorithm
    public ArrayList<Integer> listPrimes(int n) {
        int[] sieve = new int[n];
        ArrayList<Integer> primes = new ArrayList<>();

        // add beginning primes manually
        primes.add(2);

        // create a list of numbers 2-n
        for (int i = 2; i < n; i++) {
            sieve[i] = i;
        }

        // let p = 2, the smallest prime, and mark all numbers that are divisible by 2
        for (int p = 0; p < n; p += 2) {
            sieve[p] = 0;
        }

        // find the next "unmarked" number and add it to the primes.
        // Remove all its factors from the list
        for (int i = 0; i < n; i++) {
            if (sieve[i] != 0) {
                // add prime to list
                primes.add(sieve[i]);
                int prime = sieve[i];

                // remove all factors of prime
                for (int pf = i; pf < n; pf += prime) {
                    sieve[pf] = 0;
                }
            }
        }

        return primes;
    }

    // return the prime factors of a factorial using Legendre's formula
    // this assumes that this.primes has been initialized
    private ArrayList<Integer> primeFactorFactorial(int n) {
        ArrayList<Integer> divisiblePrimes = new ArrayList<>();
        ArrayList<Integer> primeFactors = new ArrayList<>();

        if (primes.isEmpty() || n > depth)
            throw new IndexOutOfBoundsException("Integer " + n + " is larger than initialized Primes");

        // get list of primes that divide into n
        int primeIter = 0;
        while (primes.get(primeIter) < n || n < primeIter-1) {
            divisiblePrimes.add(primes.get(primeIter));
            primeIter++;
        }

        // use Legendre's formula to compute the number of times
        // each prime divides into the the factorial
        for (int p : divisiblePrimes) {

            // compute each prime power of p
            for (int pPower = p; pPower < n; pPower *= p) {

                // find how many times the prime power divides into n.
                // for each division, add the prime to the array of divisible primes
                int div = (int) Math.floor(n / pPower);
                for (int i = 0; i < div; i++)
                    primeFactors.add(p);
            }
        }

        return primeFactors;
    }

    // get a map of the times the prime factor divides into the factorial of n for n between 0 and depth, inclusive
    private int[] genPrimeFactorLookup(int depth, int prime) {
        int[] lookup = new int[depth+1];

        // needs to only be done for 5 and 3
        int div = 0;

        for (int i = 0; i <= depth; i++) {
            div = 0;

            for (int p = prime; p < i; p *= prime) {
                div += i / p;
            }

            lookup[i] = div;
        }

        return lookup;
    }

    public int computeLayer() {
        /*
        We can expand the out to mean n C (x, y, z), where n is the layer of the pyramid we are on
        x and y are as they appear in binomial theorem,and z our current level on the triangle

        This an be more clearly expressed as n! / (x! * z! * y!)
        */

        int divisible = 0;
        int n = depth;
        int iter = 0;

        int x, y, z;

        // iterate through layers of the triangle (rows)
        for (int row = 0; row < n+1; row++) {
            x = 0;
            y = n - row;
            z = row;

            // iterate through each index on layer (cols)
            for (int col = 0; col < (n+1) - row; col++) {

                /*
                https://janmr.com/blog/2010/10/prime-factors-of-factorial-numbers/
                the prime factorization of 10e12 is 2e6 * 5e6, so we can just check if
                the (numerator - denominator) has > 6 fives and > 6 twos for the factorial
                expression n! / (!x !z !y)
                */

                // compute five prime factors and two prime factors in numerator and denominator
                int totalFives = 49998;
                int totalTwos = 199994;

                for (int d : new int[]{x, y, z}) {
                    totalFives -= fivesLookup[d];
                    totalTwos  -= twosLookup[d];
                }

                // if the fives and twos in the numerator - demoninator >= 12, then
                // the number is evenly divisible by 10e12
                if (totalFives >= 12 && totalTwos >= 12)
                    divisible++;

                x++;
                y--;
            }

            iter++;

            if (iter % 100 == 0)
                System.out.println(iter);
        }

        return divisible;
    }

    public static void main(String[] args) {
        Solution s = new Solution(200000);

        System.out.println(Arrays.toString(s.genPrimeFactorLookup(17, 2)));
    }
}