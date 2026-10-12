package com.plagiarism.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plagiarism.demo.model.PlagiarismRequest;
import com.plagiarism.demo.model.PlagiarismResponse;
import com.plagiarism.demo.service.PlagiarismService;

@RestController
@RequestMapping("/api/plagiarism")
@CrossOrigin(origins = "http://localhost:5173")
public class PlagiarismController {

    private final PlagiarismService service;

    public PlagiarismController(
            PlagiarismService service) {

        this.service = service;
    }


    @GetMapping("/test")
    public String test() {

        return "DSA-3 Plagiarism Checker Backend is Running";
    }


    @PostMapping("/check")
    public ResponseEntity<PlagiarismResponse> check(
            @RequestBody PlagiarismRequest request) {

        if (request.getSubmittedText() == null
                || request.getReferenceText() == null) {

            return ResponseEntity.badRequest().build();
        }

        PlagiarismResponse response =
                service.analyze(
                        request.getSubmittedText(),
                        request.getReferenceText()
                );

        return ResponseEntity.ok(response);
    }
}