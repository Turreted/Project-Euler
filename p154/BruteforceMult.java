import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.*;


public class Solution {

    // compute the size of the layer using size = n(n+1) / 2
    public static int computeSize(int n) {
        return (n * (n+1)) / 2;
    }

    public static ArrayList<BigInteger> computePascalsTriangle(int depth) {
        ArrayList<BigInteger> row = new ArrayList<>();
        row.add(BigInteger.ONE);

        for (int i = 0; i < depth; i++) {
            row.add(row.get(i).multiply(BigInteger.valueOf(depth - i)).divide(BigInteger.valueOf(i+1)));
        }

        return row;
    }

    public static void main(String[] args) {
        int depth = 20000;
        BigInteger divisor1 = BigInteger.valueOf((long)10e5);
        BigInteger divisor2 = BigInteger.valueOf((long)2e12);

        ArrayList<BigInteger> row = computePascalsTriangle(depth);

        int iter = 0;
        int res = 0;
        int total = computeSize(depth);

        for (int j = depth-1; j > 0; j--) {
            BigInteger mult1 = row.get(j);
            ArrayList<BigInteger> col = computePascalsTriangle(j);

            for (int i = 0; i < col.size(); i++) {
                BigInteger mult2 = col.get(i);
            }

            iter++;
            // System.out.println();

            if (iter % 20 == 0)
                System.out.println("At " + ((double)iter / (double)depth) * 100 + "% at row " + iter);
        }

        System.out.println(res);
    }
}


/* TODO:
1. precompute Pascal's triangle
2. check both numbers in lookup table boefore

*/