package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Queue;
import java.util.Set;

public class web_crawler {
    public static Set<String> crawl(String startingUrl, String saveDir, Set<String> visitedUrls, Queue<String> urlsToVisit, int maxUrlsToVisit) throws IOException {
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        urlsToVisit.add(startingUrl);
        while (!urlsToVisit.isEmpty() && visitedUrls.size() < maxUrlsToVisit) {
            String url = urlsToVisit.poll();
            if (!visitedUrls.contains(url)) {
                visitedUrls.add(url);
                System.out.println("Visiting: " + url);

                // Parse the page and extract links
                String links = HTMLParser.parse(url, saveDir); // Placeholder: Ensure HTMLParser is implemented.
                for (String nextUrl : links.split(" ")) {
                    if (!visitedUrls.contains(nextUrl)) {
                        urlsToVisit.add(nextUrl);
                    }
                }
            }
        }
        System.out.println("Website is crawled!");
        return visitedUrls;
    }
}
