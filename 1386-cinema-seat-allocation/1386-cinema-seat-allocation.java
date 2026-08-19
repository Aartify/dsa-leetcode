import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        // Store reserved seats for each row using a bitmask
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            // Only seats 2 to 9 matter
            if (col >= 2 && col <= 9) {
                map.put(row, map.getOrDefault(row, 0) | (1 << col));
            }
        }

        // Every completely empty row can fit 2 families
        long answer = (long) (n - map.size()) * 2;

        for (int mask : map.values()) {

            // Seats 2,3,4,5
            boolean left = (mask & ((1 << 2) | (1 << 3) |
                                   (1 << 4) | (1 << 5))) == 0;

            // Seats 4,5,6,7
            boolean middle = (mask & ((1 << 4) | (1 << 5) |
                                      (1 << 6) | (1 << 7))) == 0;

            // Seats 6,7,8,9
            boolean right = (mask & ((1 << 6) | (1 << 7) |
                                     (1 << 8) | (1 << 9))) == 0;

            if (left && right) {
                answer += 2;
            } else if (left || middle || right) {
                answer += 1;
            }
        }

        return (int) answer;
    }
}