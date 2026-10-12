package com.plagiarism.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plagiarism.demo.algorithm.AhoCorasick;
import com.plagiarism.demo.algorithm.EditDistance;
import com.plagiarism.demo.algorithm.KMPAlgorithm;
import com.plagiarism.demo.algorithm.RabinKarpAlgorithm;
import com.plagiarism.demo.algorithm.RandomizedHashing;
import com.plagiarism.demo.algorithm.SequenceAlignment;
import com.plagiarism.demo.algorithm.SuffixArray;
import com.plagiarism.demo.model.PlagiarismResponse;

@Service
public class PlagiarismService {

    private final TextProcessor textProcessor;

    private final KMPAlgorithm kmp;

    private final RabinKarpAlgorithm rabinKarp;

    private final EditDistance editDistance;

    private final SuffixArray suffixArray;

    private final SequenceAlignment alignment;

    private final RandomizedHashing randomizedHashing;


    public PlagiarismService(
            TextProcessor textProcessor) {

        this.textProcessor = textProcessor;

        this.kmp = new KMPAlgorithm();

        this.rabinKarp =
                new RabinKarpAlgorithm();

        this.editDistance =
                new EditDistance();

        this.suffixArray =
                new SuffixArray();

        this.alignment =
                new SequenceAlignment();

        this.randomizedHashing =
                new RandomizedHashing();
    }


    public PlagiarismResponse analyze(
            String submittedText,
            String referenceText) {

        String submitted =
                textProcessor.normalize(
                        submittedText
                );

        String reference =
                textProcessor.normalize(
                        referenceText
                );

        String[] submittedWords =
                textProcessor.tokenize(
                        submitted
                );

        String[] referenceWords =
                textProcessor.tokenize(
                        reference
                );


        PlagiarismResponse response =
                new PlagiarismResponse();


        if (submittedWords.length == 0
                || referenceWords.length == 0) {

            response.setSimilarity(0);
            response.setStatus("No Text");

            return response;
        }


        // --------------------------------
        // 1. Matching Words
        // --------------------------------

        List<String> matchingWords =
                textProcessor.matchingWords(
                        submittedWords,
                        referenceWords
                );

        response.setMatchingWords(
                matchingWords
        );

        response.setMatchedWords(
                matchingWords.size()
        );

        response.setTotalWords(
                submittedWords.length
        );


        // --------------------------------
        // 2. Create 3-word phrases
        // --------------------------------

        List<String> phrases =
                textProcessor.uniquePhrases(
                        referenceWords,
                        3,
                        30
                );


        response.setMatchingPhrases(
                phrases
        );


        // --------------------------------
        // 3. KMP
        // --------------------------------

        int kmpMatches = 0;

        for (String phrase : phrases) {

            kmpMatches +=
                    kmp.countOccurrences(
                            submitted,
                            phrase
                    );
        }

        response.setKmpMatches(
                kmpMatches
        );


        // --------------------------------
        // 4. Rabin-Karp
        // --------------------------------

        int rabinMatches = 0;

        for (String phrase : phrases) {

            rabinMatches +=
                    rabinKarp.countOccurrences(
                            submitted,
                            phrase
                    );
        }

        response.setRabinKarpMatches(
                rabinMatches
        );


        // --------------------------------
        // 5. Aho-Corasick
        // --------------------------------

        int ahoMatches = 0;

        if (!phrases.isEmpty()) {

            AhoCorasick aho =
                    new AhoCorasick(phrases);

            ahoMatches =
                    aho.search(submitted);
        }

        response.setAhoCorasickMatches(
                ahoMatches
        );


        // --------------------------------
        // 6. Edit Distance
        // --------------------------------

        int edit =
                editDistance.calculate(
                        submittedWords,
                        referenceWords
                );

        double editSimilarity =
                editDistance.similarity(
                        submittedWords,
                        referenceWords
                );

        response.setEditDistance(edit);

        response.setEditSimilarity(
                round(editSimilarity)
        );


        // --------------------------------
        // 7. Suffix Array
        // --------------------------------

        String commonSubstring =
                suffixArray.longestCommonSubstring(
                        submitted,
                        reference
                );

        response.setLongestCommonSubstring(
                commonSubstring
        );

        int commonWords =
                commonSubstring.isEmpty()
                        ? 0
                        : commonSubstring
                            .trim()
                            .split("\\s+")
                            .length;

        double suffixSimilarity =
                (commonWords * 100.0)
                        / Math.max(
                                1,
                                Math.min(
                                        submittedWords.length,
                                        referenceWords.length
                                )
                        );

        suffixSimilarity =
                Math.min(
                        100,
                        suffixSimilarity
                );

        response.setSuffixSimilarity(
                round(suffixSimilarity)
        );


        // --------------------------------
        // 8. Sequence Alignment
        // --------------------------------

        double alignmentSimilarity =
                alignment.similarity(
                        submittedWords,
                        referenceWords
                );

        int alignmentScore =
                alignment.alignmentScore(
                        submittedWords,
                        referenceWords
                );

        response.setAlignmentSimilarity(
                round(alignmentSimilarity)
        );

        response.setAlignmentScore(
                alignmentScore
        );


        // --------------------------------
        // 9. Randomized Hashing
        // --------------------------------

        double randomizedSimilarity =
                randomizedHashing.similarity(
                        submittedWords,
                        referenceWords
                );

        randomizedSimilarity =
                Math.min(
                        100,
                        randomizedSimilarity
                );

        response.setRandomizedHashSimilarity(
                round(randomizedSimilarity)
        );


        // --------------------------------
        // 10. Word overlap
        // --------------------------------

        double wordSimilarity =
                (matchingWords.size() * 100.0)
                        / Math.max(
                                1,
                                Math.min(
                                        submittedWords.length,
                                        referenceWords.length
                                )
                        );

        wordSimilarity =
                Math.min(100, wordSimilarity);


        // --------------------------------
        // 11. Final Similarity
        // --------------------------------

        double finalSimilarity =
                (
                        wordSimilarity * 0.15
                        + editSimilarity * 0.15
                        + suffixSimilarity * 0.15
                        + alignmentSimilarity * 0.15
                        + randomizedSimilarity * 0.15
                        + (kmpMatches > 0 ? 100 : 0) * 0.10
                        + (rabinMatches > 0 ? 100 : 0) * 0.10
                        + (ahoMatches > 0 ? 100 : 0) * 0.05
                );


        finalSimilarity =
                Math.min(
                        100,
                        finalSimilarity
                );


        response.setSimilarity(
                round(finalSimilarity)
        );


        // --------------------------------
        // 12. Status
        // --------------------------------

        if (finalSimilarity >= 70) {

            response.setStatus(
                    "High Plagiarism"
            );

        } else if (finalSimilarity >= 40) {

            response.setStatus(
                    "Moderate Similarity"
            );

        } else {

            response.setStatus(
                    "Low Similarity"
            );
        }


        return response;
    }


    private double round(double value) {

        return Math.round(
                value * 100.0
        ) / 100.0;
    }
}