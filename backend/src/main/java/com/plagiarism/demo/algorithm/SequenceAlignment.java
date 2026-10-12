package com.plagiarism.demo.algorithm;

public class SequenceAlignment {

    public double similarity(String[] a, String[] b) {

        if (a.length == 0 || b.length == 0) {
            return 0;
        }

        int[][] dp = new int[a.length + 1][b.length + 1];

        for (int i = 1; i <= a.length; i++) {
            dp[i][0] = dp[i - 1][0] - 1;
        }

        for (int j = 1; j <= b.length; j++) {
            dp[0][j] = dp[0][j - 1] - 1;
        }

        for (int i = 1; i <= a.length; i++) {

            for (int j = 1; j <= b.length; j++) {

                int matchScore =
                        a[i - 1].equals(b[j - 1]) ? 1 : -1;

                int match =
                        dp[i - 1][j - 1] + matchScore;

                int delete =
                        dp[i - 1][j] - 1;

                int insert =
                        dp[i][j - 1] - 1;

                dp[i][j] =
                        Math.max(match,
                                Math.max(delete, insert));
            }
        }

        int matches = 0;

        int i = a.length;
        int j = b.length;

        while (i > 0 && j > 0) {

            if (a[i - 1].equals(b[j - 1])) {

                matches++;
                i--;
                j--;

            } else {

                if (dp[i - 1][j - 1] >= dp[i - 1][j]
                        && dp[i - 1][j - 1] >= dp[i][j - 1]) {

                    i--;
                    j--;

                } else if (dp[i - 1][j] >= dp[i][j - 1]) {

                    i--;

                } else {

                    j--;
                }
            }
        }

        return (matches * 100.0)
                / Math.max(a.length, b.length);
    }

    public int alignmentScore(String[] a, String[] b) {

        if (a.length == 0 || b.length == 0) {
            return 0;
        }

        int[][] dp = new int[a.length + 1][b.length + 1];

        for (int i = 1; i <= a.length; i++) {
            dp[i][0] = dp[i - 1][0] - 1;
        }

        for (int j = 1; j <= b.length; j++) {
            dp[0][j] = dp[0][j - 1] - 1;
        }

        for (int i = 1; i <= a.length; i++) {

            for (int j = 1; j <= b.length; j++) {

                int score =
                        a[i - 1].equals(b[j - 1]) ? 1 : -1;

                dp[i][j] = Math.max(
                        dp[i - 1][j - 1] + score,
                        Math.max(
                                dp[i - 1][j] - 1,
                                dp[i][j - 1] - 1
                        )
                );
            }
        }

        return dp[a.length][b.length];
    }
}