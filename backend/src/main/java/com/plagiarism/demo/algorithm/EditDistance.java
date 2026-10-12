package com.plagiarism.demo.algorithm;

public class EditDistance {

    public int calculate(String[] a, String[] b) {

        int[][] dp =
                new int[a.length + 1][b.length + 1];

        for (int i = 0; i <= a.length; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= b.length; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= a.length; i++) {

            for (int j = 1; j <= b.length; j++) {

                int cost =
                        a[i - 1].equals(b[j - 1])
                                ? 0
                                : 1;

                dp[i][j] = Math.min(
                        dp[i - 1][j] + 1,
                        Math.min(
                                dp[i][j - 1] + 1,
                                dp[i - 1][j - 1] + cost
                        )
                );
            }
        }

        return dp[a.length][b.length];
    }

    public double similarity(String[] a, String[] b) {

        int distance = calculate(a, b);

        int maxLength =
                Math.max(a.length, b.length);

        if (maxLength == 0) {
            return 100;
        }

        return (1.0 -
                ((double) distance / maxLength))
                * 100;
    }
}