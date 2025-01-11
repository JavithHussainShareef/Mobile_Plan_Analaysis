package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HTMLParser {

    public static String parse(String url, String saveDir) throws IOException {
        Set<String> links = new HashSet<>();

        // Fetch and parse the HTML document from the URL
        Document doc = Jsoup.connect(url).get();

        // Extract and save the HTML content to a file
        File file = new File(saveDir, sanitizeFilename(url) + ".txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(doc.html());
        }

        // Extract all anchor tags with href attributes (links)
        Elements anchorTags = doc.select("a[href]");
        for (Element anchor : anchorTags) {
            String link = anchor.absUrl("href"); // Get absolute URL
            if (!link.isEmpty() && link.startsWith("http")) {
                links.add(link); // Add the link to the set if it's a valid URL
            }
        }

        // Join links with a space delimiter to return as a single string
        return String.join(" ", links);
    }

    public static String sanitizeFilename(String url) {
        return url.replaceAll("[^a-zA-Z0-9.-]", "_"); // Replace special characters
    }
}

