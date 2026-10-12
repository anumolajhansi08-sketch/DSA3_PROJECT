package com.plagiarism.demo.algorithm;

import java.util.*;

public class AhoCorasick {

    private static class Node {
        Map<Character, Node> next = new HashMap<>();
        Node fail;
        int output;
    }

    private final Node root = new Node();

    public AhoCorasick(List<String> patterns) {

        for (String pattern : patterns) {
            insert(pattern);
        }

        buildFailureLinks();
    }

    private void insert(String pattern) {

        Node current = root;

        for (char ch : pattern.toCharArray()) {

            current.next.putIfAbsent(ch, new Node());
            current = current.next.get(ch);
        }

        current.output++;
    }

    private void buildFailureLinks() {

        Queue<Node> queue = new LinkedList<>();

        root.fail = root;

        for (Node child : root.next.values()) {

            child.fail = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {

            Node current = queue.poll();

            for (Map.Entry<Character, Node> entry : current.next.entrySet()) {

                char ch = entry.getKey();
                Node child = entry.getValue();

                Node fallback = current.fail;

                while (fallback != root &&
                       !fallback.next.containsKey(ch)) {

                    fallback = fallback.fail;
                }

                if (fallback.next.containsKey(ch)
                        && fallback.next.get(ch) != child) {

                    child.fail = fallback.next.get(ch);

                } else {

                    child.fail = root;
                }

                child.output += child.fail.output;

                queue.add(child);
            }
        }
    }

    public int search(String text) {

        Node current = root;
        int matches = 0;

        for (char ch : text.toCharArray()) {

            while (current != root &&
                    !current.next.containsKey(ch)) {

                current = current.fail;
            }

            if (current.next.containsKey(ch)) {

                current = current.next.get(ch);

            } else {

                current = root;
            }

            matches += current.output;
        }

        return matches;
    }
}