//package org.example.search;
//
//import java.util.*;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//
//public class PageRanking {
//    // Page class encapsulates information about a web page
//    class WebPage {
//        String pageName; // The name of the web page
//        int score; // Score based on keyword matches
//
//        // Constructor to initialize the web page attributes
//        WebPage(String name, int score) {
//            this.pageName = name;
//            this.score = score;
//        }
//    }
//
//    // Main method to perform web page ranking
//    public void rankWebPages(String inputKeywords) {
//        // Directory containing the web pages
//        String webPagesDirectory = "C:\\Users\\ADMIN\\eclipse-workspace\\Final_Project\\HTML_Parser_Folder";
//        File folder = new File(webPagesDirectory);
//
//        // Get all files from the directory
//        File[] listOfFiles = folder.listFiles();
//
//        // List to store input keywords
//        List<String> keywords = new ArrayList<>();
//
//        // Process input keywords
//        for (String keyword : inputKeywords.split(",")) {
//            keywords.add(keyword.trim().toLowerCase());
//        }
//
//
//        // A Map to store the frequency of each keyword
//        Map<String, Integer> keywordFrequencies = new HashMap<>();
//
//        // Initialize keyword frequencies to 0
//        for (String keyword : keywords) {
//            keywordFrequencies.put(keyword, 0);
//        }
//
//        // Priority queue to store web pages based on their scores
//        PriorityQueue<WebPage> pageHeap = new PriorityQueue<>(10, Comparator.comparingInt(p -> p.score));
//
//        // Iterate through each web page
//        for (File file : listOfFiles) {
//            if (file.getName().endsWith(".txt")) {
//                int score = calculateScore(file, keywords, keywordFrequencies);
//                WebPage page = new WebPage(file.getName(), score);
//                pageHeap.offer(page);
//            }
//        }
//
//        // Print the top 10 web pages based on keyword matches
//        System.out.println("Top web pages based on keyword matches:\n");
//        List<WebPage> topPages = new ArrayList<>();
//        for (int i = 1; i < 10; i++) {
//            if (pageHeap.isEmpty()) {
//                break;
//            }
//            WebPage page = pageHeap.poll();
//            topPages.add(page);
//            System.out.println(i + ". " + page.pageName + " - score: " + page.score);
//        }
//    }
//
//    // Helper method to calculate the score for a web page
//    private int calculateScore(File file, List<String> keywords, Map<String, Integer> keywordFrequencies) {
//        int score = 0;
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                StringTokenizer tokenizer = new StringTokenizer(line);
//                while (tokenizer.hasMoreTokens()) {
//                    String word = tokenizer.nextToken().toLowerCase().replaceAll("[^a-z0-9]+", "");
//
//                    if (keywords.contains(word)) {
//                        score += keywordFrequencies.getOrDefault(word, 0) + 1;
//                        keywordFrequencies.put(word, keywordFrequencies.getOrDefault(word, 0) + 1);
//                    }
//                }
//            }
//
//            // Reset keyword frequencies to 0
//            for (String keyword : keywords) {
//                keywordFrequencies.put(keyword, 0);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return score;
//    }
//}


package org.example.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class PageRanking {
    // Page class encapsulates information about a web page
    class WebPage {
        String pageName; // The name of the web page
        int score; // Score based on keyword matches

        // Constructor to initialize the web page attributes
        WebPage(String name, int score) {
            this.pageName = name;
            this.score = score;
        }
    }

    // Main method to perform web page ranking
    public void rankWebPages(String inputKeywords) {
        // Directory containing the web pages
        String webPagesDirectory = "C:\\Users\\chira\\Desktop\\JavaCodes\\Final_Project\\HTML_Parser_Folder";
        File folder = new File(webPagesDirectory);

        // Get all files from the directory
        File[] listOfFiles = folder.listFiles();

        // List to store input keywords
        List<String> keywords = new ArrayList<>();

        // Process input keywords
        for (String keyword : inputKeywords.split(",")) {
            keywords.add(keyword.trim().toLowerCase());
        }

        // Priority queue to store web pages based on their scores
        PriorityQueue<WebPage> pageHeap = new PriorityQueue<>(10, Comparator.comparingInt(p -> p.score));

        // Iterate through each web page
        for (File file : listOfFiles) {
            if (file.getName().endsWith(".txt")) {
                // Create a new map for keyword frequencies for each file (web page)
                Map<String, Integer> keywordFrequencies = new HashMap<>();

                // Initialize keyword frequencies to 0
                for (String keyword : keywords) {
                    keywordFrequencies.put(keyword, 0);
                }

                // Calculate the score for the web page
                int score = calculateScore(file, keywords, keywordFrequencies);
                WebPage page = new WebPage(file.getName(), score);
                pageHeap.offer(page);
            }
        }
         Stack<String> outputStack = new Stack<>();
        // Print all web pages based on their scores (not limited to top 10)
        System.out.println("All web pages based on keyword matches:\n");
        List<WebPage> allPages = new ArrayList<>();
        while (!pageHeap.isEmpty()) {
            WebPage page = pageHeap.poll();
            allPages.add(page);
            outputStack.push(page.pageName + " - score: " + page.score);
            //System.out.println(page.pageName + " - score: " + page.score);
        }
        while (!outputStack.isEmpty()) {
            System.out.println(outputStack.pop());
        }
    }

    // Helper method to calculate the score for a web page
    private int calculateScore(File file, List<String> keywords, Map<String, Integer> keywordFrequencies) {
        int score = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Use a regex to split the line into words, removing non-alphanumeric characters
                String[] words = line.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+");

                // Count keyword occurrences in the line
                for (String word : words) {
                    if (keywords.contains(word)) {
                        // Increment the score based on the keyword match
                        score += 1;
                        keywordFrequencies.put(word, keywordFrequencies.getOrDefault(word, 0) + 1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return score;
    }
}
