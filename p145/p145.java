public class Solution {

    Boolean digitsAreOdd(long num){
        String str = String.valueOf(num);
        char ch[] = str.toCharArray();

        for (char c: ch) {
            int digit = Character.getNumericValue(c);

            if (digit % 2 == 0) return false;
        }

        return true;
    }

    long reversedInt(long num) {
        long reversed = 0;

        for(;num != 0; num /= 10) {
            long digit = num % 10;
            reversed = reversed * 10 + digit;
        }

        return reversed;
    }

    public static void main(String[] args){
        int total = 1000000000;

        Solution e = new Solution();
        int res = 0;

        for (int i = 0; i < total; i++) {
            if (i % 10 != 0) {
                long rev = e.reversedInt(i);
                if (e.digitsAreOdd(i + rev)) res++;
            }
        }

        System.out.println(res);
    }


}
