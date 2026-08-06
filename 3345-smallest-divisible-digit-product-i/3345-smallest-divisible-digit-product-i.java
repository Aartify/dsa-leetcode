class Solution {
    public int smallestNumber(int n, int t) {
        int num = n;

        while (true) {
            int product = digitProduct(num);

            if (product % t == 0) {
                return num;
            }

            num++;
        }
    }

    private int digitProduct(int num) {
        int product = 1;

        while (num > 0) {
            product *= (num % 10);
            num /= 10;
        }

        return product;
    }
}