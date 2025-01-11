package org.example.search;

import java.io.*;
        import java.util.*;
        import java.util.regex.*;

public class InvertedIndex {

    // Inverted index data structure to store words and their document occurrences
    private Map<String, Set<String>> invertedIndex;

    // Constructor to initialize the inverted index
    public InvertedIndex() {
        invertedIndex = new HashMap<>();
    }

    // Method to process all files in a directory and build the inverted index
    public void buildIndex(String directoryPath) {
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();

        // Check if the folder exists and contains files
        if (listOfFiles == null) {
            System.out.println("No files found in the directory.");
            return;
        }

        // Iterate through each file in the directory
        for (File file : listOfFiles) {
            if (file.getName().endsWith(".txt")) {
                try {
                    // Read the file content
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        processLine(file.getName(), line);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Print the inverted index for debugging purposes
        printInvertedIndex();
    }

    // Method to process each line of the text and update the inverted index
    private void processLine(String fileName, String line) {
        // Convert the line to lowercase and split by non-alphanumeric characters
        String[] words = line.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+");

        // Iterate through each word and update the inverted index
        for (String word : words) {
            if (!word.isEmpty()) {
                invertedIndex.computeIfAbsent(word, k -> new HashSet<>()).add(fileName);
            }
        }
    }

    // Method to search for a word in the inverted index and print the list of files where it appears
    public void InvertedIndexSearch(String word) {
        word = word.toLowerCase(); // Case-insensitive search
        if (invertedIndex.containsKey(word)) {
            System.out.println("The word \"" + word + "\" is found in the following files:");
            for (String file : invertedIndex.get(word)) {
                System.out.println(file);
            }
        } else {
            System.out.println("The word \"" + word + "\" is not found in any files.");
        }
    }

    // Method to print the entire inverted index (for debugging purposes)
    public void printInvertedIndex() {
        System.out.println("\nInverted Index:");
        for (Map.Entry<String, Set<String>> entry : invertedIndex.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
