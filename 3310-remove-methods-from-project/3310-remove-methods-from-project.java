import java.util.*;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {

        // Create adjacency list
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Add edges
        for (int[] edge : invocations) {
            graph[edge[0]].add(edge[1]);
        }

        // Mark suspicious methods
        boolean[] suspicious = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(k);
        suspicious[k] = true;

        while (!queue.isEmpty()) {
            int curr = queue.poll();

            for (int next : graph[curr]) {
                if (!suspicious[next]) {
                    suspicious[next] = true;
                    queue.offer(next);
                }
            }
        }

        // Check if any non-suspicious method calls a suspicious method
        for (int[] edge : invocations) {
            int from = edge[0];
            int to = edge[1];

            if (!suspicious[from] && suspicious[to]) {
                List<Integer> ans = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    ans.add(i);
                }
                return ans;
            }
        }

        // Return all remaining (non-suspicious) methods
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                ans.add(i);
            }
        }

        return ans;
    }
}