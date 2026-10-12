package com.plagiarism.demo.algorithm;

public class KMPAlgorithm {

    public int countOccurrences(
            String text,
            String pattern) {

        if (pattern == null ||
                pattern.isEmpty()) {

            return 0;
        }

        int[] lps = buildLPS(pattern);

        int i = 0;
        int j = 0;
        int count = 0;

        while (i < text.length()) {

            if (text.charAt(i) ==
                    pattern.charAt(j)) {

                i++;
                j++;

                if (j == pattern.length()) {

                    count++;
                    j = lps[j - 1];
                }

            } else {

                if (j > 0) {

                    j = lps[j - 1];

                } else {

                    i++;
                }
            }
        }

        return count;
    }

    private int[] buildLPS(String pattern) {

        int[] lps =
                new int[pattern.length()];

        int length = 0;

        int i = 1;

        while (i < pattern.length()) {

            if (pattern.charAt(i) ==
                    pattern.charAt(length)) {

                lps[i] = ++length;
                i++;

            } else {

                if (length > 0) {

                    length = lps[length - 1];

                } else {

                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }
}