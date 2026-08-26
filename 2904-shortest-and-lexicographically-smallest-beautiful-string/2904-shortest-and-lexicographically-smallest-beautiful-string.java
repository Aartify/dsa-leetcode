public class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        List<Integer> ones = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                ones.add(i);
            }
        }

        // If total 1s are less than k, no beautiful substring exists
        if (ones.size() < k) {
            return "";
        }

        int minLen = Integer.MAX_VALUE;
        String result = "";

        // Check every window of k ones
        for (int i = 0; i <= ones.size() - k; i++) {
            int start = ones.get(i);
            int end = ones.get(i + k - 1);
            int currentLen = end - start + 1;

            String sub = s.substring(start, end + 1);

            if (currentLen < minLen) {
                minLen = currentLen;
                result = sub;
            } else if (currentLen == minLen) {
                if (sub.compareTo(result) < 0) {
                    result = sub;
                }
            }
        }

        return result;
    }
}