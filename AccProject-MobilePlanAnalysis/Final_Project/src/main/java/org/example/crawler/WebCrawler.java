package org.example.crawler;

import org.example.parser.HTMLParser;

import java.io.*;

import java.net.MalformedURLException;

import java.net.URL;

import java.util.HashSet;

import java.util.LinkedList;

import java.util.Queue;

import java.util.Set;

public class WebCrawler {
    // ✓ Web crawler;
    public Set<String> crawl(String startingUrl, String saveDir, int maxUrlsToVisit) throws IOException {
        Set<String> visitedUrls = new HashSet<>();
        Queue<String> urlsToVisit = new LinkedList<>();
        // Ensure the save directory exists
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        // Add the starting URL to the queue
        urlsToVisit.add(startingUrl);
        while (!urlsToVisit.isEmpty() && visitedUrls.size() < maxUrlsToVisit) {
            try {
                String url = urlsToVisit.poll();
                // Skip already visited URLs
                if (!visitedUrls.contains(url)) {
                    visitedUrls.add(url);
                    System.out.println("Visiting: " + url);
                    // Parse the page and extract links
                    Set<String> links = HTMLParser.parse(url, saveDir); // Ensure HTMLParser is implemented
                    for (String rawLink : links) {
                        try {
                            // Resolve relative URLs to absolute
                            URL absoluteUrl = new URL(new URL(url), rawLink);
                            if (!visitedUrls.contains(absoluteUrl.toString())) {
                                urlsToVisit.add(absoluteUrl.toString());
                            }
                        } catch (MalformedURLException e) {
                            // Log and skip malformed URLs
                            System.err.println("Malformed URL: " + rawLink);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred during crawling: " + e.getMessage());
            }
        }
        System.out.println("Website is crawled!");
        return visitedUrls;

    }

}

