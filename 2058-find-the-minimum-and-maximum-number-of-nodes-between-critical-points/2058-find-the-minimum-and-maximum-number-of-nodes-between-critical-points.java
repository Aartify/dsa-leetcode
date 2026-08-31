class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return new int[]{-1, -1};
        }

        int firstCriticalIndex = -1;
        int prevCriticalIndex = -1;
        int minDistance = Integer.MAX_VALUE;

        ListNode prev = head;
        ListNode curr = head.next;
        int idx = 1; // 0-indexed position of curr

        while (curr.next != null) {
            ListNode next = curr.next;

            // Check if current node is a local maxima or minima
            boolean isMaxima = curr.val > prev.val && curr.val > next.val;
            boolean isMinima = curr.val < prev.val && curr.val < next.val;

            if (isMaxima || isMinima) {
                if (firstCriticalIndex == -1) {
                    firstCriticalIndex = idx;
                } else {
                    minDistance = Math.min(minDistance, idx - prevCriticalIndex);
                }
                prevCriticalIndex = idx;
            }

            prev = curr;
            curr = next;
            idx++;
        }

        // If fewer than 2 critical points are found
        if (firstCriticalIndex == -1 || prevCriticalIndex == firstCriticalIndex) {
            return new int[]{-1, -1};
        }

        int maxDistance = prevCriticalIndex - firstCriticalIndex;
        return new int[]{minDistance, maxDistance};
    }
}