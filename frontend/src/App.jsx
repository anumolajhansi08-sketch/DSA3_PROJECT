import { useState } from "react";
import axios from "axios";

import "./App.css";

function App() {

    const [submittedText, setSubmittedText] = useState("");

    const [referenceText, setReferenceText] = useState("");

    const [result, setResult] = useState(null);

    const [loading, setLoading] = useState(false);

    const [error, setError] = useState("");


    // Count words
    const countWords = (text) => {

        if (!text.trim()) {
            return 0;
        }

        return text.trim().split(/\s+/).length;
    };


    // Check plagiarism
    const checkPlagiarism = async () => {

        setError("");

        setResult(null);


        if (!submittedText.trim()) {

            setError(
                "Please enter the submitted document."
            );

            return;
        }


        if (!referenceText.trim()) {

            setError(
                "Please enter the reference document."
            );

            return;
        }


        try {

            setLoading(true);


            const response = await axios.post(
                "http://localhost:8080/api/plagiarism/check",
                {
                    submittedText: submittedText,
                    referenceText: referenceText
                }
            );


            setResult(response.data);


        } catch (err) {

            console.error(err);

            setError(
                "Unable to connect to the Java backend. Make sure Spring Boot is running on port 8080."
            );


        } finally {

            setLoading(false);
        }
    };


    // Clear everything
    const clearAll = () => {

        setSubmittedText("");

        setReferenceText("");

        setResult(null);

        setError("");
    };


    return (

        <div className="app">


            {/* NAVBAR */}

            <nav className="navbar">

                <div className="logo">
                    PlagiaCheck
                </div>


                <div className="nav-links">

                    <a href="#home">
                        Home
                    </a>

                    <a href="#checker">
                        Checker
                    </a>

                    <a href="#about">
                        About
                    </a>

                </div>

            </nav>



            {/* HERO SECTION */}

            <section
                className="hero"
                id="home"
            >

                <div className="hero-content">

                    <div className="badge">
                        DSA-3 Based Project
                    </div>


                    <h1>
                        Word-Level
                        <br />
                        Plagiarism Checker
                    </h1>


                    <p>
                        Detect copied and similar content
                        using advanced string matching
                        and dynamic programming algorithms.
                    </p>


                    <a
                        href="#checker"
                        className="start-button"
                    >
                        Start Checking
                    </a>

                </div>

            </section>



            {/* CHECKER */}

            <section
                className="checker"
                id="checker"
            >

                <div className="section-heading">

                    <h2>
                        Plagiarism Checker
                    </h2>

                    <p>
                        Compare your submitted document
                        with a reference document.
                    </p>

                </div>



                {/* DOCUMENT BOXES */}

                <div className="documents">


                    {/* SUBMITTED */}

                    <div className="document-box">

                        <div className="box-header">

                            <h3>
                                Submitted Document
                            </h3>

                            <span>
                                {countWords(
                                    submittedText
                                )} words
                            </span>

                        </div>


                        <textarea
                            value={submittedText}
                            onChange={(e) =>
                                setSubmittedText(
                                    e.target.value
                                )
                            }
                            placeholder="Enter or paste your submitted document here..."
                        />

                    </div>



                    {/* REFERENCE */}

                    <div className="document-box">

                        <div className="box-header">

                            <h3>
                                Reference Document
                            </h3>

                            <span>
                                {countWords(
                                    referenceText
                                )} words
                            </span>

                        </div>


                        <textarea
                            value={referenceText}
                            onChange={(e) =>
                                setReferenceText(
                                    e.target.value
                                )
                            }
                            placeholder="Enter or paste your reference document here..."
                        />

                    </div>

                </div>



                {/* ERROR */}

                {error && (

                    <div className="error-message">
                        {error}
                    </div>

                )}



                {/* BUTTONS */}

                <div className="button-area">

                    <button
                        className="check-button"
                        onClick={checkPlagiarism}
                        disabled={loading}
                    >

                        {loading
                            ? "Analyzing..."
                            : "Check Plagiarism"
                        }

                    </button>


                    <button
                        className="clear-button"
                        onClick={clearAll}
                    >
                        Clear
                    </button>

                </div>



                {/* RESULT */}

                {result && (

                    <div className="result-card">

                        <h2>
                            Plagiarism Analysis Result
                        </h2>

                        <p className="result-subtitle">
                            Analysis completed using
                            DSA-3 algorithms
                        </p>



                        {/* SCORE */}

                        <div className="score-circle">

                            <div>

                                <strong>
                                    {result.similarity}%
                                </strong>

                                <span>
                                    Similarity
                                </span>

                            </div>

                        </div>



                        <div className="status">

                            {result.status}

                        </div>



                        {/* STATISTICS */}

                        <div className="statistics">


                            <div className="stat-box">

                                <strong>
                                    {result.totalWords}
                                </strong>

                                <span>
                                    Total Words
                                </span>

                            </div>


                            <div className="stat-box">

                                <strong>
                                    {result.matchedWords}
                                </strong>

                                <span>
                                    Matched Words
                                </span>

                            </div>


                            <div className="stat-box">

                                <strong>
                                    {result.editDistance}
                                </strong>

                                <span>
                                    Edit Distance
                                </span>

                            </div>

                        </div>



                        {/* ALGORITHM RESULTS */}

                        <div className="algorithm-results">

                            <h3>
                                DSA-2 Algorithm Results
                            </h3>


                            <div className="algorithm-grid">


                                <div className="algorithm-card">

                                    <h4>
                                        KMP
                                    </h4>

                                    <strong>
                                        {result.kmpMatches}
                                    </strong>

                                    <span>
                                        Matching phrases
                                    </span>

                                </div>


                                <div className="algorithm-card">

                                    <h4>
                                        Rabin-Karp
                                    </h4>

                                    <strong>
                                        {result.rabinKarpMatches}
                                    </strong>

                                    <span>
                                        Hash matches
                                    </span>

                                </div>


                                <div className="algorithm-card">

                                    <h4>
                                        Edit Distance
                                    </h4>

                                    <strong>
                                        {result.editSimilarity}%
                                    </strong>

                                    <span>
                                        Text similarity
                                    </span>

                                </div>

                            </div>

                        </div>



                        {/* MATCHING WORDS */}

                        <div className="matches">

                            <h3>
                                Matching Words
                            </h3>


                            {result.matchingWords &&
                            result.matchingWords.length > 0 ? (

                                <div className="tags">

                                    {result.matchingWords.map(
                                        (word, index) => (

                                            <span
                                                key={index}
                                            >
                                                {word}
                                            </span>

                                        )
                                    )}

                                </div>

                            ) : (

                                <p className="no-match">
                                    No matching words found.
                                </p>

                            )}

                        </div>



                        {/* MATCHING PHRASES */}

                        <div className="matches">

                            <h3>
                                Matching Phrases
                            </h3>


                            {result.matchingPhrases &&
                            result.matchingPhrases.length > 0 ? (

                                result.matchingPhrases.map(
                                    (phrase, index) => (

                                        <div
                                            className="phrase"
                                            key={index}
                                        >
                                            {phrase}
                                        </div>

                                    )
                                )

                            ) : (

                                <p className="no-match">
                                    No matching phrases found.
                                </p>

                            )}

                        </div>

                    </div>

                )}

            </section>



            {/* ABOUT */}

            <section
                className="about"
                id="about"
            >

                <div>

                    <h2>
                        About the Project
                    </h2>

                    <p>
                        The Word-Level Plagiarism Checker
                        is a DSA-3 based web application
                        designed to identify similar and
                        copied content between documents.
                        The system uses KMP pattern matching,
                        Rabin-Karp rolling hash and
                        Levenshtein edit distance to analyze
                        textual similarity.
                    </p>

                </div>

            </section>



            {/* FOOTER */}

            <footer>

                <p>
                    Word-Level Plagiarism Checker
                    | DSA-3 Project
                </p>

            </footer>

        </div>
    );
}

export default App;