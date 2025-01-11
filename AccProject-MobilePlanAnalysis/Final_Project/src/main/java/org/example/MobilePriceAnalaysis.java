package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.example.crawler.WebCrawler;
import org.example.search.FrequencyCount;
import org.example.search.PageRanking;
import org.example.search.SearchFrequency;
import org.example.search.SpellChecker;
import org.example.search.WordCompletion;
import org.json.JSONArray;
import org.json.JSONObject;

public class MobilePriceAnalaysis {
    private static int wrongAttempts = 0; // Counter for wrong attempts
    private static final List<String> carriers = Arrays.asList("Rogers", "Bell", "Freedom", "Koodo", "Telus");
    private static final Map<String, List<String>> samplePlans = new HashMap<>(); // Example plans for spellchecking

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize SpellChecker and WordCompletion
        SpellChecker spellChecker = new SpellChecker();
        WordCompletion wordCompletion = new WordCompletion();

        try {
            // Build vocabulary for spell checking
            spellChecker.buildVocabulary("HTML_Parser_Folder");

            // Execute word completion setup
            //wordCompletion.WordCompletion_Execute("HTML_Parser_Folder");
        } catch (IOException e) {
            System.out.println("Error initializing spell checking or word completion: " + e.getMessage());
            return;
        }

        // Initialize sample data for spellchecking and word completion
        initializeSampleData();

        // Step 1: Database Selection
        databaseSelection(scanner);

        // Step 2: Main Menu
        mainMenu(scanner);

        scanner.close();
    }

    public static void Crawling() {
        String saveDir = "MobileWebCrawlDir";
        int maxUrlsToVisit = 10;
        List<String> allCrawledSites = new ArrayList<>();

        WebCrawler webCrawler = new WebCrawler();
        String[] mySites = {"https://www.bell.ca/","https://www.rogers.com/","https://www.freedommobile.ca/","https://www.koodomobile.com/","https://www.telus.com/"};
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
    }

    /**
     * Step 1: Ask the user whether to use an existing database or crawl new data.
     */
    private static void databaseSelection(Scanner scanner) {
        boolean validChoice = false;



        while (!validChoice) {
            System.out.println("=======================================");
            System.out.println("       Mobile Data Plan Analysis       ");
            System.out.println("=======================================");
            System.out.println("Do you want to:");
            System.out.println("1. Use Existing Crawled Database");
            System.out.println("2. Create a New Database by Crawling");
            System.out.print("Enter your choice (1 or 2): ");

            String choice = scanner.nextLine();


            switch (choice) {
                case "1":
                    System.out.println("You have chosen to use the existing database.\n");
                    validChoice = true;
                    break;
                case "2":
                    System.out.println("You have chosen to create a new database by crawling.\n");
                    validChoice = true;
                    Crawling();
                    break;
                default:
                    wrongAttempts++;
                    System.out.println("Invalid option. Please enter 1 or 2.");
                    System.out.println("Wrong attempts so far: " + wrongAttempts + "\n");
            }
        }
    }

    /**
     * Step 2: Main Menu
     */
private static void mainMenu(Scanner scanner) {
    boolean exit = false;

    while (!exit) {
        System.out.println("=======================================");
        System.out.println("       Mobile Data Plan Analysis       ");
        System.out.println("=======================================");
        System.out.println("1. View All Carriers");
        System.out.println("2. View Best Plan");
        System.out.println("3. Frequency Analysis (Count and Search)");
        System.out.println("4. Page Ranking and Inverted Index");
        System.out.println("5. Exit");
        System.out.print("Select an option: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                viewAllCarriers(scanner);
                break;
            case "2":
                showTopPlans(scanner);
                break;
            case "3":
                // Combined Frequency Count and Search Frequency feature
                System.out.println("\nFrequency Analysis:");
                FrequencyCount frequencyCount = new FrequencyCount();
                SearchFrequency searchFrequency = new SearchFrequency();

                // Frequency Count
                System.out.println("\nWord frequency analysis");
                boolean validUrl = false;
                while (!validUrl) {
                    System.out.print("Please provide a valid URL (e.g., https://www.example.com): ");
                    String url = scanner.nextLine();

                    // Basic validation for URL format
                    if (url.matches("https?://[\\w.-]+(:\\d+)?(/\\S*)?")) {
                        try {
                            frequencyCount.countWordFrequency(url);
                            validUrl = true; // Exit the loop if URL is valid and processed
                        } catch (IOException e) {
                            System.out.println("Error processing the URL. Please ensure the URL is accessible and try again.");
                        }
                    } else {
                        System.out.println("Invalid URL format. Make sure it starts with 'http://' or 'https://'. Case sensitivity matters.");
                    }
                }
                break;

            case "4":
                // Combined Page Ranking and Inverted Index feature
                System.out.println("\nPage Ranking and Inverted Index:");
                PageRanking pageRanking = new PageRanking();
                System.out.print("\nEnter keywords for Page Ranking (comma-separated): ");
                String keywords = scanner.nextLine();
                pageRanking.rankWebPages(keywords);
                break;

            case "5":
                System.out.println("Exiting the application. Goodbye!");
                exit = true;
                break;

            default:
                wrongAttempts++;
                System.out.println("Invalid option. Please try again.");
                System.out.println("Wrong attempts so far: " + wrongAttempts + "\n");
        }
    }
}


    /**
     * View all carriers and let the user select one.
     */
    private static void viewAllCarriers(Scanner scanner) {
        boolean backToMenu = false;
        SpellChecker spellChecker = new SpellChecker();
        WordCompletion wordCompletion = new WordCompletion();

        while (!backToMenu) {
            System.out.println("\nAvailable Mobile Carriers:");
            carriers.forEach(System.out::println); // Display all carriers
            System.out.println("Type 'BACK' to return to the main menu.");
            System.out.print("Enter the name of a service provider (case-sensitive): ");

            String input = scanner.nextLine();

            // Check if input is a number (invalid)
            if (input.matches("\\d+")) {
                System.out.println("Invalid input. Please enter a valid carrier name, not a number.");
                wrongAttempts++;
                System.out.println("Wrong attempts so far: " + wrongAttempts + "\n");
                continue;  // Skip the rest of the loop and prompt for input again
            }

            if (input.equals("BACK")) {
                backToMenu = true;
                System.out.println("Returning to the main menu...\n");
            } else if (carriers.contains(input)) {
                exploreCarrier(scanner, input); // Go into carrier-specific menu
            } else {
                wrongAttempts++;

                // Perform spell checking
                String suggestion = SpellChecker.Updatedfindnear(input, new HashSet<>(carriers));
                if (!suggestion.isEmpty()) {
                    System.out.println("Did you mean: " + suggestion);
                } else {
                    System.out.println("No suggestions available for input: " + input);
                }

                List<String> completions = new ArrayList<>();

                if (!completions.isEmpty()) {
                    System.out.println("Suggestions based on your input:");
                    completions.forEach(System.out::println);
                } else {
                    System.out.println("No completions found for input: " + input);
                }

                System.out.println("Wrong attempts so far: " + wrongAttempts + "\n");
            }
        }
    }

    private static void searchForFeature(Scanner scanner, String carrier) {

        // Load the plans data

        Map<String, JSONArray> plansData = loadPlans("src\\main\\java\\org\\example\\utils\\data.json");

        System.out.println("\nSearching for a specific feature in " + carrier + " plans.");

        System.out.print("Enter the feature you want to search for (e.g., '5G', 'Unlimited Data', 'Canada-wide calling'): ");

        String feature = scanner.nextLine().toLowerCase();

        // Initialize SpellChecker and WordCompletion

        SpellChecker spellChecker = new SpellChecker();

        WordCompletion wordCompletion = new WordCompletion();

        String vocabularySourceDir = "C:\\Users\\chira\\Desktop\\JavaCodes\\Final_Project\\SearchForFeature_VocabFile";

        try {

            // Build vocabulary for SpellChecker and WordCompletion

            spellChecker.buildVocabulary(vocabularySourceDir);

            wordCompletion.buildTrie(vocabularySourceDir);

            // Execute spell check

            System.out.println("Checking the spelling of the feature...");

            spellChecker.execute_SpellCheck(feature);

            // Execute word completion

            System.out.println("Fetching word completions...");

            List<String> completions = wordCompletion.getCompletions(feature);

            if (!completions.isEmpty()) {

                System.out.println("Word completions for \"" + feature + "\":");

                completions.forEach(System.out::println);

            }

            // Get corrected or completed feature from the user

            System.out.print("If you meant a suggestion, enter it here (or press Enter to continue with \"" + feature + "\"): ");

            String correctedFeature = scanner.nextLine().trim().toLowerCase();

            // Use the corrected feature if provided, otherwise use the original input

            feature = correctedFeature.isEmpty() ? feature : correctedFeature;

        } catch (IOException e) {

            System.out.println("Error in spell checking or word completion: " + e.getMessage());

            System.out.println("Proceeding with the entered feature: \"" + feature + "\".");

        }

        // Check if the carrier exists in the plans data

        if (!plansData.containsKey(carrier)) {

            System.out.println("No plans available for the selected carrier.\n");

            return;

        }

        // Fetch the plans for the carrier

        JSONArray plans = plansData.get(carrier);

        boolean featureFound = false;

        // Search through the plans for the specified feature

        for (int i = 0; i < plans.length(); i++) {

            JSONObject plan = plans.getJSONObject(i);

            // Match the feature in the plan details

            if (plan.toString().toLowerCase().contains(feature)) {

                featureFound = true;

                System.out.println("\nMatching Plan:");

                System.out.println("Plan Type: " + plan.getString("planType"));

                System.out.println("Plan Name: " + plan.getString("planName"));

                System.out.println("Monthly Cost: " + plan.getString("monthlyCost"));

                System.out.println("Data Allowance: " + plan.getString("dataAllowance"));

                System.out.println("Network Coverage: " + plan.getString("networkCoverage"));

                System.out.println("Call and Text Allowance: " + plan.getString("callAndTextAllowance"));

                System.out.println();

            }

        }

        // If no feature was found

        if (!featureFound) {

            System.out.println("No plans matching the feature \"" + feature + "\" were found for " + carrier + ".");

        }

    }


    private static void exploreCarrier(Scanner scanner, String carrier) {
        boolean backToCarriers = false;

        while (!backToCarriers) {
            System.out.println("\n=======================================");
            System.out.println("       " + carrier + " Service Options");
            System.out.println("=======================================");
            System.out.println("1. Analyze Plans for " + carrier);
            System.out.println("2. Search for a Specific Feature");
            System.out.println("3. Return to Carrier Selection");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayPlanDetails(carrier);
                    break;
                case "2":
                    searchForFeature(scanner, carrier);
                    break;
                case "3":
                    backToCarriers = true;
                    System.out.println("Returning to carrier selection...\n");
                    break;
                default:
                    wrongAttempts++;
                    System.out.println("Invalid option. Please try again.");
                    System.out.println("Wrong attempts so far: " + wrongAttempts + "\n");
            }
        }
    }
    private static void showTopPlans(Scanner scanner) {
        Map<String, JSONArray> plansData = loadPlans("src\\main\\java\\org\\example\\utils\\data.json"); // Update with your JSON file path
        if (plansData == null || plansData.isEmpty()) {
            System.out.println("No plans available for analysis.");
            return;
        }

        List<JSONObject> allPlans = new ArrayList<>();
        for (Map.Entry<String, JSONArray> entry : plansData.entrySet()) {
            String carrier = entry.getKey();
            JSONArray plans = entry.getValue();
            for (int i = 0; i < plans.length(); i++) {
                JSONObject plan = plans.getJSONObject(i);
                plan.put("carrier", carrier); // Add carrier name to the plan object
                allPlans.add(plan);
            }
        }

        System.out.println("\nChoose an option:");
        System.out.println("1. Top 5 Plans by Price");
        System.out.println("2. Top 5 Plans by Data (Descending Order)");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1": // Sort by price (ascending)
                allPlans.stream()
                        .sorted(Comparator.comparingDouble(plan -> parseCost(plan.getString("monthlyCost"))))
                        .limit(5)
                        .forEach(plan -> displayFullPlanDetails(plan));
                break;

            case "2": // Sort by data (descending)
                allPlans.stream()
                        .sorted((p1, p2) -> Double.compare(parseData(p2.getString("dataAllowance")), parseData(p1.getString("dataAllowance"))))
                        .limit(5)
                        .forEach(plan -> displayFullPlanDetails(plan));
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    /**
     * Parse the monthly cost from the plan.
     */
    private static double parseCost(String monthlyCost) {
        try {
            String cost = monthlyCost.replaceAll("[^0-9.]", "");
            return Double.parseDouble(cost);
        } catch (NumberFormatException e) {
            return Double.MAX_VALUE; // If parsing fails, assign a high value to push it to the end
        }
    }

    /**
     * Parse the maximum data allowance from the plan.
     */
    private static double parseData(String dataAllowance) {
        try {
            String[] parts = dataAllowance.split(" ");
            for (String part : parts) {
                if (part.endsWith("GB")) {
                    return Double.parseDouble(part.replace("GB", ""));
                } else if (part.endsWith("MB")) {
                    return Double.parseDouble(part.replace("MB", "")) / 1024; // Convert MB to GB
                }
            }
        } catch (Exception e) {
            // Handle parsing errors gracefully
        }
        return 0.0; // Return 0 if no data allowance is found
    }

    /**
     * Display the full details of a plan.
     */
    private static void displayFullPlanDetails(JSONObject plan) {
        System.out.println("=======================================");
        System.out.println("Carrier: " + plan.getString("carrier"));
        System.out.println("\"planType\": \"" + plan.getString("planType") + "\",");
        System.out.println("\"planName\": \"" + plan.getString("planName") + "\",");
        System.out.println("\"monthlyCost\": \"" + plan.getString("monthlyCost") + "\",");
        System.out.println("\"dataAllowance\": \"" + plan.getString("dataAllowance") + "\",");
        System.out.println("\"networkCoverage\": \"" + plan.getString("networkCoverage") + "\",");
        System.out.println("\"callAndTextAllowance\": \"" + plan.getString("callAndTextAllowance") + "\"");
        System.out.println("=======================================");
    }

    /**
     * Analyze plans for a specific carrier.
     */
    private static void analyzePlansForCarrier(Scanner scanner, String carrier) {
        System.out.println("\nAnalyzing plans for " + carrier + "...");
        // TODO: Integrate analysis logic for individual carrier plans
        System.out.println("Analysis completed for " + carrier + ".\n");
    }

    /**
     * Analyze data plans across all providers.
     */
    private static void analyzeDataPlans(Scanner scanner) {
        System.out.println("\nAnalyzing data plans across all providers...");
        // TODO: Integrate data analysis logic
        System.out.println("Data analysis completed successfully.\n");
    }

    /**
     * Search for specific features in plans.
     */
    private static void searchForSpecificFeatures(Scanner scanner) {
        System.out.print("\nEnter the feature to search (e.g., '5G', 'Unlimited Data'): ");
        String feature = scanner.nextLine();

        String suggestion = spellCheck(feature, new ArrayList<>(samplePlans.keySet()));
        System.out.println("Searching for feature: " + (suggestion.equals(feature) ? feature : suggestion));
        // TODO: Integrate search functionality
        System.out.println("Search results for " + (suggestion.equals(feature) ? feature : suggestion) + " displayed successfully.\n");
    }


    /**
     * Rank pages by relevance.
     */
    private static void rankPagesByRelevance(Scanner scanner) {
        System.out.println("\nRanking pages by relevance...");
        System.out.println("Enter the keyword to rank pages: ");
        String keyword = scanner.nextLine();
        // TODO: Replace with actual ranking logic
        System.out.println("Rankings displayed successfully for: " + keyword + "\n");
    }

    /**
     * Display plan details for the selected carrier.
     */

    private static Map<String, JSONArray> loadPlans(String filePath) {
        Map<String, JSONArray> plansMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            for (String key : jsonObject.keySet()) {
                plansMap.put(key, jsonObject.getJSONArray(key));
            }
        } catch (IOException e) {
            System.err.println("Error loading plan data: " + e.getMessage());
        }

        return plansMap;
    }

    private static void displayPlanDetails(String carrier, Map<String, JSONArray> plansData) {
        System.out.println("\nFetching plan details for " + carrier + "...\n");

        if (!plansData.containsKey(carrier)) {
            System.out.println("No plans available for the selected carrier.\n");
            return;
        }

        JSONArray plans = plansData.get(carrier);

        for (int i = 0; i < plans.length(); i++) {
            JSONObject plan = plans.getJSONObject(i);
            System.out.println("Plan Type: " + plan.getString("planType"));
            System.out.println("Plan Name: " + plan.getString("planName"));
            System.out.println("Monthly Cost: " + plan.getString("monthlyCost"));
            System.out.println("Data Allowance: " + plan.getString("dataAllowance"));
            System.out.println("Network Coverage: " + plan.getString("networkCoverage"));
            System.out.println("Call and Text Allowance: " + plan.getString("callAndTextAllowance"));
            System.out.println();
        }

        System.out.println("Plan details displayed successfully.\n");
    }

    private static void displayPlanDetails(String carrier) {
        System.out.println("\nFetching plan details for " + carrier + "...");
        System.out.println("Plan details for " + carrier + " displayed successfully.\n");
        Map<String, JSONArray>  plans = loadPlans("C:\\Users\\chira\\Desktop\\JavaCodes\\Final_Project\\src\\main\\java\\org\\example\\utils\\data.json");
        displayPlanDetails( carrier, plans);
    }

    /**
     * Spellcheck or suggest a correct option.
     */
    private static String spellCheck(String input, List<String> options) {
        for (String option : options) {
            if (option.toLowerCase().contains(input.toLowerCase())) {
                return option;
            }
        }
        return input; // If no match found, return original input
    }

    /**
     * Initialize sample plans for spellchecking.
     */
    private static void initializeSampleData() {
        samplePlans.put("5G", Arrays.asList("Rogers 5G", "Bell 5G"));
        samplePlans.put("Unlimited Data", Arrays.asList("AT&T Unlimited", "Telus Unlimited"));
        samplePlans.put("Family Plan", Arrays.asList("Freedom Family Plan"));
    }
}



