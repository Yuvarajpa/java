public class KnapsackWeightOnly {
    public static void main(String[] args) {
        int[] weight = {10, 120, 50};
        int capacity = 50;
        int n = weight.length;

        int[][] dp = new int[n + 1][capacity + 1];

        // Fill DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (weight[i - 1] <= w) {
                    // include or exclude the item
                    dp[i][w] = Math.max(dp[i - 1][w], weight[i - 1] + dp[i - 1][w - weight[i - 1]]);
                } else {
                    dp[i][w] = dp[i - 1][w]; // can't include item
                }
            }
        }

        System.out.println("Maximum total weight within capacity: " + dp[n][capacity]);
    }
}
