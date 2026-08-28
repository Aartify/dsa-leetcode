class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();
        int halfLen = n / 2;

        int[] freq = new int[26];

        // Count characters
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Check if palindrome is possible
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        // Build frequency of the first half
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        // Target's first half
        String targetHalf = target.substring(0, halfLen);

        /*
         * First check whether targetHalf itself can be formed.
         * If yes, the resulting palindrome might be > target
         * because of the middle/right half.
         */
        int[] temp = halfFreq.clone();

        boolean canMakeTargetHalf = true;

        for (int i = 0; i < halfLen; i++) {
            int c = targetHalf.charAt(i) - 'a';

            if (temp[c] == 0) {
                canMakeTargetHalf = false;
                break;
            }

            temp[c]--;
        }

        if (canMakeTargetHalf) {

            char[] half = targetHalf.toCharArray();

            String candidate = makePalindrome(half, middle);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * Find the smallest permutation of halfFreq
         * that is strictly greater than targetHalf.
         *
         * We try the rightmost possible position to increase.
         */
        for (int pivot = halfLen - 1; pivot >= 0; pivot--) {

            int[] remaining = halfFreq.clone();

            // Match targetHalf before pivot
            boolean possible = true;

            for (int i = 0; i < pivot; i++) {

                int c = targetHalf.charAt(i) - 'a';

                if (remaining[c] == 0) {
                    possible = false;
                    break;
                }

                remaining[c]--;
            }

            if (!possible) {
                continue;
            }

            int targetChar = targetHalf.charAt(pivot) - 'a';

            /*
             * At pivot, choose the smallest available character
             * that is greater than target[pivot].
             */
            for (int c = targetChar + 1; c < 26; c++) {

                if (remaining[c] == 0) {
                    continue;
                }

                char[] half = new char[halfLen];

                // Copy prefix
                for (int i = 0; i < pivot; i++) {
                    half[i] = targetHalf.charAt(i);
                }

                // Make pivot larger
                half[pivot] = (char) ('a' + c);

                remaining[c]--;

                // Fill rest with smallest possible characters
                int pos = pivot + 1;

                for (int x = 0; x < 26; x++) {
                    while (remaining[x] > 0) {
                        half[pos++] = (char) ('a' + x);
                        remaining[x]--;
                    }
                }

                return makePalindrome(half, middle);
            }
        }

        return "";
    }

    private String makePalindrome(char[] half, char middle) {

        StringBuilder sb = new StringBuilder();

        // First half
        for (char c : half) {
            sb.append(c);
        }

        // Middle character
        if (middle != 0) {
            sb.append(middle);
        }

        // Second half
        for (int i = half.length - 1; i >= 0; i--) {
            sb.append(half[i]);
        }

        return sb.toString();
    }
}