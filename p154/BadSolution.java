import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Solution {

    public HashMap<Integer, BigInteger> factorialLookup = new HashMap<>();
    public int depth;

    public Solution(int depth) {
        System.out.println("Filling factorial lookup table...");
        fillLookupTable(depth);

        this.depth = depth;
    }

    public static BigInteger[][] computePascalsTriangle(int depth) {


        // create jagged arrays of triangle
        BigInteger[][] triangle = new BigInteger[depth][];
        triangle[0] = new BigInteger[]{BigInteger.ONE};
        triangle[1] = new BigInteger[]{BigInteger.ONE, BigInteger.ONE};

        for (int d = 2; d < depth; d++) {
            // compute by summing the previous layer

            triangle[d] = new BigInteger[d+1];
            triangle[d][0] = BigInteger.ONE;
            triangle[d][d] = BigInteger.ONE;

            // sum previous row. I could do this faster but I'm lazy
            for (int i = 1; i < d; i++) {
                triangle[d][i] = triangle[d-1][i-1].add(triangle[d-1][i]);
            }

            System.out.println(d);
        }


        return triangle;
    }

    // fills the lookup table with the first n factorial numbers
    public void fillLookupTable(int n) {
        BigInteger factorial = BigInteger.ONE;
        factorialLookup.put(0, factorial);
        factorialLookup.put(1, factorial);

        for (int i = 2; i <= n; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
            factorialLookup.put(i, factorial);
        }
    }

    // compute the factorial of a number
    public BigInteger factorial(int num) {
        BigInteger factorial = BigInteger.ONE;

        for (int i = num; i > 0; i--) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }

        return factorial;
    }

    // compute the size of the layer using size = n(n+1) / 2
    public int computeSize(int n) {
        return (n * (n+1)) / 2;
    }

    public int[] primeFactor(int f) {
        return new int[]{1, 2, 3};
    }

    // compute the trinomial at a position
    // This an be more clearly expressed as n! / (x! * z! * y!)
    public BigInteger computeTrinomial(int n, int x, int z, int y) {
        BigInteger bn = factorialLookup.get(n);
        BigInteger bx = factorialLookup.get(x);
        BigInteger bz = factorialLookup.get(y);
        BigInteger by = factorialLookup.get(z);

        return bn.divide(bx.multiply(bz).multiply(by));
    }

    // compute the layer of pascal's pyramid coefficients at a given index
    /*
    1A4B0C0 + 4A3B0C1 + 6A2B0C2 + 4A1B0C3 + 1A0B0C4 +
        4A3B1C0 + 12A2B1C1 + 12A1B1C2 + 4A0B1C3 +
            6A2B2C0 + 12A1B2C1 + 6A0B2C2 +
                4A1B3C0 + 4A0B3C1 +
                    1A0B4C0

    We can expand the out to mean n C (x, y, z), where n is the layer of the pyramid we are on
    x and y are as they appear in binomial theorem,and z our current level on the triangle

    This an be more clearly expressed as n! / (x! * z! * y!)

    NOTE that index is zero-indexed
    */
    public int computeLayer() {
        int divisible = 0;
        int n = this.depth;

        BigDecimal divisorDouble = BigDecimal.valueOf(10e12);
        BigInteger divisor = divisorDouble.toBigInteger();

        int x, y, z;
        int iter = 0;

        // iterate through layers of the triangle (rows)
        for (int row = 0; row < n+1; row++) {
            x = 0;
            y = n - row;
            z = row;

            // iterate through each index on layer (cols)
            for (int col = 0; col < (n + 1) - row; col++) {
                //System.out.println(x + ", " + z + ", " + y);

                /*
                https://janmr.com/blog/2010/10/prime-factors-of-factorial-numbers/
                the prime factorization of 10e12 is 2e6 * 5e6, so we can just check if
                the numerator has > 6 fives and > 6 twos
                */
                BigInteger t = computeTrinomial(n, x, z, y);
                System.out.print(t + " ");

                // value = n! / (x! * z! * y!)
                x++;
                y--;
            }
            System.out.println();
        }

        return divisible;
    }

    public static void main(String[] args) {
        Solution s = new Solution(1000);
        System.out.println(s.computeTrinomial(1000, 569, 137, 294));
        System.out.println(s.computeSize(20000));
    }
}