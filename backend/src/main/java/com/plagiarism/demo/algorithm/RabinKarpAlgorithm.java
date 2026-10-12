package com.plagiarism.demo.algorithm;

public class RabinKarpAlgorithm {

    private static final long MOD = 1_000_000_007L;
    private static final long BASE = 256;

    public int countOccurrences(
            String text,
            String pattern) {

        if (pattern == null ||
                pattern.isEmpty() ||
                pattern.length() > text.length()) {

            return 0;
        }

        int m = pattern.length();

        long patternHash = 0;
        long textHash = 0;
        long power = 1;

        for (int i = 0; i < m - 1; i++) {
            power = (power * BASE) % MOD;
        }

        for (int i = 0; i < m; i++) {

            patternHash =
                    (patternHash * BASE
                            + pattern.charAt(i))
                            % MOD;

            textHash =
                    (textHash * BASE
                            + text.charAt(i))
                            % MOD;
        }

        int count = 0;

        for (int i = 0;
             i <= text.length() - m;
             i++) {

            if (patternHash == textHash) {

                if (text.regionMatches(
                        i,
                        pattern,
                        0,
                        m)) {

                    count++;
                }
            }

            if (i < text.length() - m) {

                textHash =
                        (BASE *
                                (textHash
                                        - text.charAt(i)
                                        * power)
                                + text.charAt(i + m))
                                % MOD;

                if (textHash < 0) {
                    textHash += MOD;
                }
            }
        }

        return count;
    }
}