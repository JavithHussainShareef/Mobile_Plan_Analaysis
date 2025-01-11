package org.example.search;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileReader;

import java.io.IOException;

import java.util.*;

public class WordCompletion {

    // TrieNode class represents each node in the Trie

    static class TrieNode {

        Map<Character, TrieNode> children = new HashMap<>();

        boolean isEndOfWord = false;

    }

    // Trie class for managing word insertions and completions

    static class Trie {

        private TrieNode root;

        // Constructor

        public Trie() {

            root = new TrieNode();

        }

        // Insert a word into the Trie

        public void insert(String word) {

            TrieNode node = root;

            for (char ch : word.toCharArray()) {

                node = node.children.computeIfAbsent(ch, c -> new TrieNode());

            }

            node.isEndOfWord = true;

        }

        // Add all words from a list to the Trie

        public void addAll(List<String> words) {

            for (String word : words) {

                insert(word);

            }

        }

        // Search for words that match the given prefix

        public List<String> getCompletions(String prefix) {

            List<String> completions = new ArrayList<>();

            TrieNode node = root;

            // Traverse the Trie to find the node corresponding to the prefix

            for (char ch : prefix.toCharArray()) {

                node = node.children.get(ch);

                if (node == null) {

                    return completions; // No words found with this prefix

                }

            }

            // Perform a DFS to find all words with this prefix

            findWordsWithPrefix(node, prefix, completions);

            return completions;

        }

        // Helper function to perform DFS and find words

        private void findWordsWithPrefix(TrieNode node, String prefix, List<String> completions) {

            if (node.isEndOfWord) {

                completions.add(prefix); // Add word if it's complete

            }

            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {

                findWordsWithPrefix(entry.getValue(), prefix + entry.getKey(), completions);

            }

        }

    }

    private final Trie trie = new Trie();

    // Build the Trie from vocabulary files

    public void buildTrie(String sourceDir) throws IOException {

        File dir = new File(sourceDir);

        if (!dir.exists() || !dir.isDirectory()) {

            throw new IOException("Directory not found: " + sourceDir);

        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {

            throw new IOException("No text files found in directory: " + sourceDir);

        }

        for (File file : files) {

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] words = line.toLowerCase().split("\\W+"); // Split by non-word characters

                    trie.addAll(Arrays.asList(words));

                }

            }

        }

        System.out.println("Trie built successfully from the vocabulary.");

    }

    // Wrapper method to get completions

    public List<String> getCompletions(String prefix) {

        return trie.getCompletions(prefix);

    }

}

 