package org.example.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrequencyCount {
    // Method to extract the text from the URL and count word frequencies
    public  void countWordFrequency(String url) throws IOException {
        // Fetch the HTML document from the URL
        Document doc = Jsoup.connect(url).get();

        // Extract the text from the document
        String text = doc.body().text();

        // Split the text into words (using non-word characters as delimiters)
        String[] words = text.toLowerCase().split("\\W+");

        // Create a map to store the frequency of each word
        Map<String, Integer> wordFrequency = new HashMap<>();

        // Count the frequency of each word
        for (String word : words) {
            if (!word.isEmpty()) {  // Avoid counting empty strings
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
        }

        // Display the word frequencies
        System.out.println("Word Frequency Count for URL: " + url);
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

//    public static void main(String[] args) {
//        // URL to be processed
//        String url = "https://www.example.com"; // Replace with the URL you want to analyze
//
//        try {
//            countWordFrequency(url);
//        } catch (IOException e) {
//            System.err.println("Error while processing the URL: " + e.getMessage());
//        }
//    }
}
