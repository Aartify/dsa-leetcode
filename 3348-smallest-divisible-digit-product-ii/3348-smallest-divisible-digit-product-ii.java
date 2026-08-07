class Solution {
    public String smallestNumber(String num, long t) {
        // Step 1: Factorize t into prime factors 2, 3, 5, 7
        long tempT = t;
        int req2 = 0, req3 = 0, req5 = 0, req7 = 0;

        while (tempT % 2 == 0) { req2++; tempT /= 2; }
        while (tempT % 3 == 0) { req3++; tempT /= 3; }
        while (tempT % 5 == 0) { req5++; tempT /= 5; }
        while (tempT % 7 == 0) { req7++; tempT /= 7; }

        // If t contains prime factors other than 2, 3, 5, 7, it's impossible
        if (tempT > 1) {
            return "-1";
        }

        int N = num.length();

        // Step 2: Store cumulative prime factor counts for prefixes of `num`
        int[][] pref = new int[N + 1][4];
        int firstZeroIdx = -1;

        for (int i = 0; i < N; i++) {
            char ch = num.charAt(i);
            if (ch == '0') {
                if (firstZeroIdx == -1) firstZeroIdx = i;
                pref[i + 1] = pref[i].clone();
            } else {
                int[] f = getFactors(ch - '0');
                pref[i + 1][0] = pref[i][0] + f[0];
                pref[i + 1][1] = pref[i][1] + f[1];
                pref[i + 1][2] = pref[i][2] + f[2];
                pref[i + 1][3] = pref[i][3] + f[3];
            }
        }

        // Check if `num` itself is zero-free and valid
        if (firstZeroIdx == -1) {
            if (minDigits(req2 - pref[N][0], req3 - pref[N][1], req5 - pref[N][2], req7 - pref[N][3]) <= 0) {
                return num;
            }
        }

        // Step 3: Try same length N by replacing digit at index i
        for (int i = N - 1; i >= 0; i--) {
            if (firstZeroIdx != -1 && i > firstZeroIdx) {
                continue;
            }

            int p2 = pref[i][0], p3 = pref[i][1], p5 = pref[i][2], p7 = pref[i][3];
            int startDigit = (firstZeroIdx != -1 && i >= firstZeroIdx) ? 1 : (num.charAt(i) - '0' + 1);

            for (int digit = startDigit; digit <= 9; digit++) {
                int[] f = getFactors(digit);
                int rem2 = req2 - p2 - f[0];
                int rem3 = req3 - p3 - f[1];
                int rem5 = req5 - p5 - f[2];
                int rem7 = req7 - p7 - f[3];

                int remLen = N - 1 - i;
                if (minDigits(rem2, rem3, rem5, rem7) <= remLen) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i).append(digit);
                    sb.append(buildSuffix(remLen, rem2, rem3, rem5, rem7));
                    return sb.toString();
                }
            }
        }

        // Step 4: If no solution of length N exists, construct the minimum needed length
        // Target length is at least N + 1, or larger if t demands more digits
        int minLenNeeded = minDigits(req2, req3, req5, req7);
        int targetLen = Math.max(N + 1, minLenNeeded);
        return buildSuffix(targetLen, req2, req3, req5, req7);
    }

    // Calculates min digits required to satisfy prime factor counts 2^a * 3^b * 5^c * 7^d
    private int minDigits(int a, int b, int c, int d) {
        a = Math.max(0, a);
        b = Math.max(0, b);
        c = Math.max(0, c);
        d = Math.max(0, d);

        int cnt8 = a / 3, remA = a % 3;
        int cnt9 = b / 2, remB = b % 2;

        int cnt = cnt8 + cnt9 + c + d;

        if (remA == 2 && remB == 1)      cnt += 2; // e.g., 4 and 3
        else if (remA == 2 && remB == 0) cnt += 1; // 4
        else if (remA == 1 && remB == 1) cnt += 1; // 6
        else if (remA == 1 && remB == 0) cnt += 1; // 2
        else if (remA == 0 && remB == 1) cnt += 1; // 3

        return cnt;
    }

    // Factorizes a single digit 1..9 into powers of [2, 3, 5, 7]
    private int[] getFactors(int digit) {
        int cnt2 = 0, cnt3 = 0, cnt5 = 0, cnt7 = 0;
        while (digit % 2 == 0) { cnt2++; digit /= 2; }
        while (digit % 3 == 0) { cnt3++; digit /= 3; }
        if (digit == 5) cnt5 = 1;
        if (digit == 7) cnt7 = 1;
        return new int[]{cnt2, cnt3, cnt5, cnt7};
    }

    // Greedily constructs the smallest valid suffix of a given length
    private String buildSuffix(int length, int rem2, int rem3, int rem5, int rem7) {
        StringBuilder sb = new StringBuilder();
        for (int step = 0; step < length; step++) {
            for (int digit = 1; digit <= 9; digit++) {
                int[] f = getFactors(digit);
                int r2 = rem2 - f[0], r3 = rem3 - f[1], r5 = rem5 - f[2], r7 = rem7 - f[3];
                
                if (minDigits(r2, r3, r5, r7) <= length - sb.length() - 1) {
                    sb.append(digit);
                    rem2 = r2; rem3 = r3; rem5 = r5; rem7 = r7;
                    break;
                }
            }
        }
        return sb.toString();
    }
}