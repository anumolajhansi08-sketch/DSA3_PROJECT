package com.plagiarism.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TextProcessor {

    public String normalize(String text) {

        if (text == null) {
            return "";
        }

        return text
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public String[] tokenize(String text) {

        String normalized = normalize(text);

        if (normalized.isEmpty()) {
            return new String[0];
        }

        return normalized.split(" ");
    }

    public List<String> createPhrases(
            String[] words,
            int size) {

        List<String> phrases =
                new ArrayList<>();

        if (words.length < size) {
            return phrases;
        }

        for (int i = 0;
             i <= words.length - size;
             i++) {

            StringBuilder phrase =
                    new StringBuilder();

            for (int j = i;
                 j < i + size;
                 j++) {

                if (phrase.length() > 0) {
                    phrase.append(" ");
                }

                phrase.append(words[j]);
            }

            phrases.add(phrase.toString());
        }

        return phrases;
    }

    public List<String> uniquePhrases(
            String[] words,
            int size,
            int limit) {

        LinkedHashSet<String> unique =
                new LinkedHashSet<>(
                        createPhrases(words, size)
                );

        return new ArrayList<>(
                unique.stream()
                        .limit(limit)
                        .toList()
        );
    }

    public List<String> matchingWords(
            String[] submitted,
            String[] reference) {

        Set<String> referenceSet =
                new HashSet<>(
                        Arrays.asList(reference)
                );

        Set<String> matches =
                new LinkedHashSet<>();

        for (String word : submitted) {

            if (referenceSet.contains(word)
                    && word.length() > 2) {

                matches.add(word);
            }
        }

        return new ArrayList<>(matches);
    }
}