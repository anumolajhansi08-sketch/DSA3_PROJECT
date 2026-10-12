package com.plagiarism.demo.algorithm;

import java.util.*;

public class RandomizedHashing {

    private final long MOD = 1_000_000_007L;

    private final long base;

    public RandomizedHashing() {

        Random random = new Random();

        base = 256 + random.nextInt(1000);
    }

    private long hash(String text) {

        long hash = 0;

        for (char ch : text.toCharArray()) {

            hash = (hash * base + ch) % MOD;
        }

        return hash;
    }

    public double similarity(
            String[] first,
            String[] second) {

        Set<Long> firstHashes =
                createHashes(first);

        Set<Long> secondHashes =
                createHashes(second);

        if (firstHashes.isEmpty() ||
                secondHashes.isEmpty()) {

            return 0;
        }

        Set<Long> common =
                new HashSet<>(firstHashes);

        common.retainAll(secondHashes);

        int minimum =
                Math.min(
                        firstHashes.size(),
                        secondHashes.size()
                );

        return (common.size() * 100.0) / minimum;
    }

    private Set<Long> createHashes(String[] words) {

        Set<Long> hashes = new HashSet<>();

        int window = 3;

        if (words.length < window) {
            window = words.length;
        }

        if (window == 0) {
            return hashes;
        }

        for (int i = 0;
             i <= words.length - window;
             i++) {

            StringBuilder phrase = new StringBuilder();

            for (int j = i;
                 j < i + window;
                 j++) {

                phrase.append(words[j]).append(" ");
            }

            hashes.add(hash(phrase.toString().trim()));
        }

        return hashes;
    }
}