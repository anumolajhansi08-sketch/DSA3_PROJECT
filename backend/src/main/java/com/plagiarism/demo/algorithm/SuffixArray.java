package com.plagiarism.demo.algorithm;

import java.util.*;

public class SuffixArray {

    public String longestCommonSubstring(String first, String second) {

        if (first == null || second == null ||
                first.isEmpty() || second.isEmpty()) {

            return "";
        }

        String separator = "\u0001";

        String combined = first + separator + second;

        int n = combined.length();

        Integer[] suffixes = new Integer[n];

        for (int i = 0; i < n; i++) {
            suffixes[i] = i;
        }

        Arrays.sort(suffixes,
                Comparator.comparing(combined::substring));

        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[i] = suffixes[i];
        }

        int bestLength = 0;
        int bestPosition = 0;

        for (int i = 1; i < n; i++) {

            int a = suffixes[i - 1];
            int b = suffixes[i];

            boolean firstPartA = a < first.length();
            boolean firstPartB = b < first.length();

            if (firstPartA == firstPartB) {
                continue;
            }

            int length = commonLength(combined, a, b);

            if (length > bestLength) {

                bestLength = length;
                bestPosition = a;
            }
        }

        return combined.substring(
                bestPosition,
                bestPosition + bestLength
        );
    }

    private int commonLength(String text, int a, int b) {

        int count = 0;

        while (a < text.length()
                && b < text.length()
                && text.charAt(a) == text.charAt(b)
                && text.charAt(a) != '\u0001') {

            count++;
            a++;
            b++;
        }

        return count;
    }
}