package org.example.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpellChecker {
    private static final Set<String> vocabulary = new HashSet<>();
    public  void buildVocabulary(String sourceDir) throws IOException {
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
                    vocabulary.addAll(Arrays.asList(words));
                }
            }
        }
//        File file = new File("HTML_Parser_Folder", sanitizeFilename("vocabulary") + ".txt");
//        // Write the vocabulary to the output file
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("vocabulary.txt"))) {
//            for (String word : vocabulary) {
//                writer.write(word);
//                writer.newLine();
//            }
//           // System.out.println("Vocabulary written to " + outputFile.getAbsolutePath());
//        } catch (IOException e) {
//            System.err.println("Error writing vocabulary to file: " + e.getMessage());
//        }

        // System.out.println("Vocabulary built with " + vocabulary.size() + " unique words.");
    }

    private static List<String> suggestWords(String input) {
        Map<String, Integer> wordDistances = new HashMap<>();
        for (String word : vocabulary) {
            int distance = calculateEditDistance(input, word);
            if (distance <= 2) { // Suggest words within an edit distance of 2
                wordDistances.put(word, distance);
            }
        }

        // Sort suggestions by edit distance
        List<Map.Entry<String, Integer>> sortedSuggestions = new ArrayList<>(wordDistances.entrySet());
        sortedSuggestions.sort(Map.Entry.comparingByValue());

        List<String> suggestions = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedSuggestions) {
            suggestions.add(entry.getKey());
        }
        return suggestions;
    }
    public static String Updatedfindnear(String mispelWd, Set<String> dic) {
        String closestWord = "";
        int minEdDis = Integer.MAX_VALUE;

        // Iterate through the dictionary (Set) to find the word with the smallest edit distance
        for (String dictWord : dic) {
            int distance = calculateEditDistance(mispelWd, dictWord);
            if (distance < minEdDis) {
                minEdDis = distance;
                closestWord = dictWord;
            }
        }
        return closestWord;
       // return closestWord + ", Edit Distance: " + minEdDis;
    }

    public static int calculateEditDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // Create a DP table to store the results of subproblems
        int[][] dp = new int[len1 + 1][len2 + 1];

        // Fill dp[][] in a bottom-up manner
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                // If the first word is empty, insert all characters of the second word
                if (i == 0) {
                    dp[i][j] = j; // j insertions
                }
                // If the second word is empty, remove all characters of the first word
                else if (j == 0) {
                    dp[i][j] = i; // i deletions
                }
                // If the last characters of both words are the same, ignore the last character
                // and recur for the remaining words
                else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // If the last characters are different, consider all possibilities:
                // insert, delete, or replace the last character
                else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], // Remove
                            Math.min(dp[i][j - 1],    // Insert
                                    dp[i - 1][j - 1])); // Replace
                }
            }
        }

        // The final value in dp[len1][len2] will be the answer
        return dp[len1][len2];
    }

    public  void execute_SpellCheck(String userInput){
        if (vocabulary.contains(userInput)) {
            System.out.println("The word '" + userInput + "' is spelled correctly.");
        } else {
            System.out.println("The word '" + userInput + "' is not in the vocabulary.");
            //List<String> suggestions = suggestWords(userInput);
            String suggestions = Updatedfindnear(userInput, vocabulary);
            if (suggestions.isEmpty()) {
                System.out.println("No suggestions found.");
            } else {
                System.out.println("Did you mean:");
//                        for (String suggestion : suggestions) {
                System.out.println(" - " + suggestions);
//                        }
            }
        }
    }
}