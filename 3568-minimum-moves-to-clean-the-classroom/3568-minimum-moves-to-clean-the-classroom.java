import java.util.*;

class Solution {

    static class State {
        int r, c, e, mask, moves;

        State(int r, int c, int e, int mask, int moves) {
            this.r = r;
            this.c = c;
            this.e = e;
            this.mask = mask;
            this.moves = moves;
        }
    }

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int startR = 0, startC = 0;
        int litterCount = 0;

        int[][] litterId = new int[m][n];

        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        // Find start and assign each litter a bit number
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startR = i;
                    startC = j;
                }

                if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        int allCollected = (1 << litterCount) - 1;

        if (litterCount == 0) {
            return 0;
        }

        Queue<State> queue = new ArrayDeque<>();

        /*
         * visited[r][c][mask]
         * stores the maximum energy with which we reached
         * this position with this litter mask.
         */
        int[][][] bestEnergy =
                new int[m][n][1 << litterCount];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(bestEnergy[i][j], -1);
            }
        }

        queue.offer(
                new State(startR, startC, energy, 0, 0)
        );

        bestEnergy[startR][startC][0] = energy;

        int[][] dirs = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        while (!queue.isEmpty()) {

            State cur = queue.poll();

            // Cannot move if energy is 0
            if (cur.e == 0) {
                continue;
            }

            for (int[] d : dirs) {

                int nr = cur.r + d[0];
                int nc = cur.c + d[1];

                // Outside grid
                if (nr < 0 || nr >= m ||
                    nc < 0 || nc >= n) {
                    continue;
                }

                char cell = classroom[nr].charAt(nc);

                // Obstacle
                if (cell == 'X') {
                    continue;
                }

                int newEnergy = cur.e - 1;
                int newMask = cur.mask;

                // Collect litter
                if (cell == 'L') {
                    int id = litterId[nr][nc];
                    newMask |= (1 << id);
                }

                // Reset energy
                if (cell == 'R') {
                    newEnergy = energy;
                }

                // All litter collected
                if (newMask == allCollected) {
                    return cur.moves + 1;
                }

                /*
                 * If we reached the same position
                 * with same collected litter but
                 * previously had MORE or equal energy,
                 * this state is useless.
                 */
                if (bestEnergy[nr][nc][newMask] >= newEnergy) {
                    continue;
                }

                bestEnergy[nr][nc][newMask] = newEnergy;

                queue.offer(
                        new State(
                                nr,
                                nc,
                                newEnergy,
                                newMask,
                                cur.moves + 1
                        )
                );
            }
        }

        return -1;
    }
}