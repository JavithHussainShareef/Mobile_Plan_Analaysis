package org.example;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.example.crawler.WebCrawler;
import org.example.parser.HTMLParser;
import org.example.search.FindingPattern;
import org.example.search.FrequencyCount;
import org.example.search.InvertedIndex;
import org.example.search.MultiValidator;
import org.example.search.PageRanking;
import org.example.search.SearchFrequency;
import org.example.search.SpellChecker;
import org.example.search.WordCompletion;

public class Main {

    public static void main(String[] args) throws IOException {
        String saveDir = "MobileWebCrawlDir";
        String saveTxt = "HTML_Parser_Folder";
        String[] mySites = {"https://www.bell.ca/"};
        int maxUrlsToVisit = 10;
        List<String> allCrawledSites = new ArrayList<>();

        WebCrawler webCrawler = new WebCrawler();
        HTMLParser htmlParser = new HTMLParser();
        SpellChecker spellChecker = new SpellChecker();
        WordCompletion wCompletion = new WordCompletion();
        FrequencyCount fCount = new FrequencyCount();
        SearchFrequency sFrequency = new SearchFrequency();
        PageRanking pageRanking = new PageRanking();
        InvertedIndex index = new InvertedIndex();
        MultiValidator mValidator = new MultiValidator();
        FindingPattern fPattern = new FindingPattern();



        for (String site : mySites) {
            System.out.println("\nStarting crawl for site: " + site);
            try {
                Set<String> result = webCrawler.crawl(site, saveDir, maxUrlsToVisit);
                System.out.println("Visited URLs for " + site + ":");
                for (String url : result) {
                    System.out.println(url);
                    allCrawledSites.add(url);
                }
            } catch (IOException e) {
                System.out.println("An error occurred during crawling: " + e.getMessage());
            }
        }

        for (String site : allCrawledSites) {
            System.out.println("\nStarting HTML Parsing for site: " + site);

            // Ensure the save directory exists
            File htmlParserDir = new File(saveTxt);
            if (!htmlParserDir.exists()) {
                htmlParserDir.mkdir();
            }

//            try {
//                // Call the parse method
////                String res = htmlParser.parse(site, "HTML_Parser_Folder");
////                System.out.println("Parsed links: " + res);
//            } catch (IOException e) {
//                System.out.println("Error parsing site " + site + ": " + e.getMessage());
//            }
        }


            try {
                spellChecker.buildVocabulary(saveTxt);

                Scanner scanner = new Scanner(System.in);
                System.out.println("Vocabulary loaded. Enter a word to check its spelling:");
                String userInput = scanner.nextLine().trim().toLowerCase();
                spellChecker.execute_SpellCheck(userInput);
                scanner.close();
            } catch (IOException e) {
                System.out.println("Error "  + e.getMessage());
            }


//            try{
//                wCompletion.WordCompletion_Execute(saveTxt);
//            }
//            catch (IOException e) {
//                System.out.println("Error " + e.getMessage());
//            }

        for (String site : allCrawledSites) {
            try {
                // Call the parse method
                fCount.countWordFrequency(site);
                System.out.println("test" );
            } catch (Exception e) {
                System.out.println("Error parsing site " + site + ": " + e.getMessage());
            }
        }

        //Search frequency;
        sFrequency.searcheachlocationfreq();

        //Page ranking
        pageRanking.rankWebPages("Bell");

        //Inverted indexing

        index.buildIndex("C:\\Users\\chira\\Desktop\\JavaCodes\\Final_Project\\HTML_Parser_Folder");
        index.InvertedIndexSearch("Bell");

        //Data validation using regular expressions;
         mValidator.MultiValidator_Execution();



        //finding patterns using regular expressions
        fPattern.searchInAllTextFiles(saveTxt);

    }


}


//        while (true) {
//
//            System.out.println("\n=== Main Menu ===");
//
//            System.out.println("1. Crawl a Web Page");
//
//            System.out.println("2. Parse a Web Page");
//
//            System.out.println("3. Check Spelling");
//
//            System.out.println("4. Word Completion");
//
//            System.out.println("5. Count Word Frequency");
//
//            System.out.println("6. View Search Frequency");
//
//            System.out.println("7. Rank Pages");
//
//            System.out.println("8. Perform Quick Search (Inverted Index)");
//
//            System.out.println("9. Validate Inputs Using Regular Expressions");
//
//            System.out.println("10. Exit");
//
//            System.out.print("Enter your choice: ");
//
//            int choice = scanner.nextInt();
//
//            scanner.nextLine(); // Consume newline
//
//            switch (choice) {
//
//                case 1 : {
//
//                    System.out.print("Enter URL to crawl: ");
//
//                    String url = scanner.nextLine();
//
//                    crawler.crawl(url);
//
//                }
//
//                case 2 : {
//
//                    System.out.print("Enter file path to parse: ");
//
//                    String filePath = scanner.nextLine();
//
//                    String content = parser.parseFile(filePath);
//
//                    System.out.println("Extracted Content:\n" + content);
//
//                }
//
//                case 3 : {
//
//                    System.out.print("Enter word to spell-check: ");
//
//                    String word = scanner.nextLine();
//
//                    String suggestion = spellChecker.suggest(word);
//
//                    System.out.println("Suggestion: " + suggestion);
//
//                }
//
//                case 4 : {
//
//                    System.out.print("Enter prefix for word completion: ");
//
//                    String prefix = scanner.nextLine();
//
//                    System.out.println("Suggestions: " + wordCompletion.complete(prefix));
//
//                }
//
//                case 5 : {
//
//                    System.out.print("Enter file path: ");
//
//                    String filePath = scanner.nextLine();
//
//                    System.out.print("Enter word to count: ");
//
//                    String word = scanner.nextLine();
//
//                    String content = parser.parseFile(filePath);
//
//                    int count = spellChecker.countWordFrequency(content, word);
//
//                    System.out.println("Word Count: " + count);
//
//                }
//
//                case 6 : {
//
//                    System.out.println("Search History:");
//
//                    searchFrequency.displayHistory();
//
//                }
//
//                case 7 : {
//
//                    System.out.println("Page Rankings:");
//
//                    pageRanking.rankPages();
//
//                }
//
//                case 8 : {
//
//                    System.out.print("Enter word to search: ");
//
//                    String query = scanner.nextLine();
//
//                    System.out.println("Documents containing '" + query + "': " + invertedIndex.search(query));
//
//                }
//
//                case 9 : {
//
//                    System.out.print("Enter input to validate (e.g., email, URL): ");
//
//                    String input = scanner.nextLine();
//
//                    System.out.println("Validation Result: " + regexValidator.validate(input));
//
//                }
//
//                case 10 : {
//
//                    System.out.println("Exiting...");
//
//                    return;
//
//                }
//
//                default : System.out.println("Invalid choice! Try again.");
//
//            }

            // }






//public class Main {
//
//    // Crawl method to traverse web pages
//    public static Set<String> crawl(String startingUrl, String saveDir, Set<String> visitedUrls, Queue<String> urlsToVisit, int maxUrlsToVisit) throws IOException {
//        File dir = new File(saveDir);
//        if (!dir.exists()) {
//            dir.mkdir();
//        }
//        urlsToVisit.add(startingUrl);
//        while (!urlsToVisit.isEmpty() && visitedUrls.size() < maxUrlsToVisit) {
//            try {
//                String url = urlsToVisit.poll();
//                if (!visitedUrls.contains(url)) {
//                    visitedUrls.add(url);
//                    System.out.println("Visiting: " + url);
//
//                    // Parse the page and extract links
//                    String links = HTMLParser.parse(url, saveDir); // Ensure HTMLParser is implemented
//                    for (String nextUrl : links.split(" ")) {
//                        if (!visitedUrls.contains(nextUrl)) {
//                            urlsToVisit.add(nextUrl);
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                System.out.println("An error occurred during crawling: " + e.getMessage());
//            }
//        }
//        System.out.println("Website is crawled!");
//        return visitedUrls;
//    }
//
//    public static void main(String[] args) {
//        Set<String> visitedUrls = new HashSet<>();
//        Queue<String> urlsToVisit = new LinkedList<>();
//        int maxUrlsToVisit = 10;
//        String saveDir = "MobileWebCrawlDir";
//        String[] mySites = {"https://www.bell.ca/", "https://www.koodomobile.com/en", "https://www.rogers.com/"};
//
//        try {
//            // Correct loop to iterate through all valid indices
//            for (int i = 0; i < mySites.length; i++) { // Fix: Loop condition is i < mySites.length
//                Set<String> result = crawl(mySites[i], saveDir, visitedUrls, urlsToVisit, maxUrlsToVisit);
//                System.out.println("Visited URLs:");
//                for (String url : result) {
//                    System.out.println(url);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred during crawling: " + e.getMessage());
//        }
//
//        System.out.printf("Hello and welcome!");
//    }
//}
