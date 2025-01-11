//package org.example.parser;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
//public class HTMLParser {
//public static String parse(String url, String saveDir) throws IOException {
//    Set<String> links = new HashSet<>();
//
//    // Fetch and parse the HTML document from the URL
//    Document doc = Jsoup.connect(url).get();
//
//    // Extract and save the HTML content to a file
//    File file = new File(saveDir, sanitizeFilename(url) + ".txt");
//    try (FileWriter writer = new FileWriter(file)) {
//        writer.write(doc.text());
//    }
//
//    // Extract all anchor tags with href attributes (links)
//    Elements anchorTags = doc.select("a[href]");
//    for (Element anchor : anchorTags) {
//        String link = anchor.absUrl("href"); // Get absolute URL
//        if (!link.isEmpty() && link.startsWith("http")) {
//            links.add(link); // Add the link to the set if it's a valid URL
//        }
//    }
//
//    // Join links with a space delimiter to return as a single string
//    return String.join(" ", links);
//}
//
//    private static String sanitizeFilename(String url) {
//        return url.replaceAll("[^a-zA-Z0-9.-]", "_"); // Replace special characters
//    }
//}

package org.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//package org.example.parser;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.HashSet;

import java.util.Set;

public class HTMLParser {
    public static Set<String> parse(String url, String saveDir) throws IOException {

        Set<String> links = new HashSet<>();

        Document doc = Jsoup.connect(url).get();

        // Extract all anchor tags with href attributes

        Elements anchorTags = doc.select("a[href]");

        for (Element link : anchorTags) {

            links.add(link.attr("href"));

        }

        // Save the page content (if needed)

        String fileName = saveDir + "/" + url.replaceAll("[^a-zA-Z0-9]", "_") + ".html";

        FileWriter writer = new FileWriter(fileName);

        writer.write(doc.html());

        writer.close();

        return links;

    }

}



//public class HTMLParser {
//
//    public static String parse(String url, String saveDir) throws IOException {
//        Set<String> links = new HashSet<>();
//
//        // Fetch and parse the HTML document from the URL with a User-Agent
//        Document doc = Jsoup.connect(url)
//                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
//                .get();
//
//        // Extract and save the visible text content (excluding HTML tags) to a file
//        File file = new File(saveDir, sanitizeFilename(url) + ".txt");
//        try (FileWriter writer = new FileWriter(file)) {
//            // Save only the visible text content
//            String visibleText = doc.body().text(); // Get visible text within the <body> tag
//            writer.write(visibleText);
//        }
//
//        // Extract all anchor tags with href attributes (links)
//        Elements anchorTags = doc.select("a[href]");
//        for (Element anchor : anchorTags) {
//            String link = anchor.absUrl("href"); // Get absolute URL
//            if (!link.isEmpty() && link.startsWith("http")) {
//                links.add(link); // Add the link to the set if it's a valid URL
//            }
//        }
//
//        // Return all extracted links as a space-separated string
//        return String.join(" ", links);
//    }
//
//    private static String sanitizeFilename(String url) {
//        return url.replaceAll("[^a-zA-Z0-9.-]", "_"); // Replace special characters
//    }
//}
