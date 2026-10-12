package com.plagiarism.demo.model;

import java.util.List;

public class PlagiarismResponse {

    private double similarity;

    private double editSimilarity;

    private int matchedWords;

    private int totalWords;

    private int kmpMatches;

    private int rabinKarpMatches;

    private int ahoCorasickMatches;

    private int editDistance;

    private double suffixSimilarity;

    private double alignmentSimilarity;

    private int alignmentScore;

    private double randomizedHashSimilarity;

    private String longestCommonSubstring;

    private String status;

    private List<String> matchingWords;

    private List<String> matchingPhrases;


    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public double getEditSimilarity() {
        return editSimilarity;
    }

    public void setEditSimilarity(double editSimilarity) {
        this.editSimilarity = editSimilarity;
    }

    public int getMatchedWords() {
        return matchedWords;
    }

    public void setMatchedWords(int matchedWords) {
        this.matchedWords = matchedWords;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    public int getKmpMatches() {
        return kmpMatches;
    }

    public void setKmpMatches(int kmpMatches) {
        this.kmpMatches = kmpMatches;
    }

    public int getRabinKarpMatches() {
        return rabinKarpMatches;
    }

    public void setRabinKarpMatches(int rabinKarpMatches) {
        this.rabinKarpMatches = rabinKarpMatches;
    }

    public int getAhoCorasickMatches() {
        return ahoCorasickMatches;
    }

    public void setAhoCorasickMatches(int ahoCorasickMatches) {
        this.ahoCorasickMatches = ahoCorasickMatches;
    }

    public int getEditDistance() {
        return editDistance;
    }

    public void setEditDistance(int editDistance) {
        this.editDistance = editDistance;
    }

    public double getSuffixSimilarity() {
        return suffixSimilarity;
    }

    public void setSuffixSimilarity(double suffixSimilarity) {
        this.suffixSimilarity = suffixSimilarity;
    }

    public double getAlignmentSimilarity() {
        return alignmentSimilarity;
    }

    public void setAlignmentSimilarity(double alignmentSimilarity) {
        this.alignmentSimilarity = alignmentSimilarity;
    }

    public int getAlignmentScore() {
        return alignmentScore;
    }

    public void setAlignmentScore(int alignmentScore) {
        this.alignmentScore = alignmentScore;
    }

    public double getRandomizedHashSimilarity() {
        return randomizedHashSimilarity;
    }

    public void setRandomizedHashSimilarity(
            double randomizedHashSimilarity) {

        this.randomizedHashSimilarity =
                randomizedHashSimilarity;
    }

    public String getLongestCommonSubstring() {
        return longestCommonSubstring;
    }

    public void setLongestCommonSubstring(
            String longestCommonSubstring) {

        this.longestCommonSubstring =
                longestCommonSubstring;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getMatchingWords() {
        return matchingWords;
    }

    public void setMatchingWords(
            List<String> matchingWords) {

        this.matchingWords = matchingWords;
    }

    public List<String> getMatchingPhrases() {
        return matchingPhrases;
    }

    public void setMatchingPhrases(
            List<String> matchingPhrases) {

        this.matchingPhrases = matchingPhrases;
    }
}