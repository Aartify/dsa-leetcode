
class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth,
                            int need1, int need2) {

        long option1 = (long) need1 * cost1
                     + (long) need2 * cost2;

        long option2 = (long) Math.min(need1, need2) * costBoth
                     + (long) Math.abs(need1 - need2)
                     * (need1 > need2 ? cost1 : cost2);

        long option3 = (long) Math.max(need1, need2) * costBoth;

        return Math.min(option1, Math.min(option2, option3));
    }
}
