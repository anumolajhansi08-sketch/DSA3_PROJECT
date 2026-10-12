package com.plagiarism.demo.model;

public class PlagiarismRequest {

    private String submittedText;

    private String referenceText;

    public String getSubmittedText() {
        return submittedText;
    }

    public void setSubmittedText(
            String submittedText) {

        this.submittedText =
                submittedText;
    }

    public String getReferenceText() {
        return referenceText;
    }

    public void setReferenceText(
            String referenceText) {

        this.referenceText =
                referenceText;
    }
}