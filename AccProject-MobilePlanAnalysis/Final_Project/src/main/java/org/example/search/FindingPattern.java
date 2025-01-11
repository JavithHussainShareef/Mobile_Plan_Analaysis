package org.example.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindingPattern {
    // folder containing converted text files
//    private static final String Updated_Dir = "text_pages";

    // Patterns of regular expression to match URLs, email addresses, and phone numbers
    private static final String Updated_Ph_Pt = "\\b\\+?[0-9]{1,4}?[-.\\s]?\\(?[0-9]{1,4}?\\)?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}\\b";
    private static final String Updated_Ema_Pt = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
    private static final String Updated_URL_Pt = "(https?://)?([a-z0-9-]+\\.)+[a-z0-9]{2,4}(/[^\\s]*)?";

    public  void searchInAllTextFiles(String Updated_Dir) {
        // Access the directory containing text
        File updated_txt_dr = new File(Updated_Dir);
        File[] updated_txt_files = updated_txt_dr.listFiles((dir, name) -> name.endsWith(".txt"));

        // Print a notice and close the window if there are no text files detected.
        if (updated_txt_files == null || updated_txt_files.length == 0) {
            System.out.println("There were no text files in " + Updated_Dir);
            return;
        }

        // Analyse every text file in the directory.
        for (File updated_txt : updated_txt_files) {
            System.out.println("\nSearching in file: " + updated_txt.getName());
            searchInFile(updated_txt);
        }
    }
    // Method to search for phone numbers, emails, and URLs within a single file
    private static  void searchInFile(File txt) {
        try (BufferedReader reader = new BufferedReader(new FileReader(txt))) {
            String ln;
            // Read each line of the file and search for patterns
            while ((ln = reader.readLine()) != null) {
                Updated_Search_Pattern(ln, Updated_Ph_Pt, "Phone Number");
                Updated_Search_Pattern(ln, Updated_Ema_Pt, "Email Address");
                Updated_Search_Pattern(ln, Updated_URL_Pt, "URL");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + txt.getName() + " - " + e.getMessage());
        }
    }
    // Method to search for a specific pattern in a line of text and print matches
    private  static void Updated_Search_Pattern(String line, String pattern, String label) {
        // Compile the regex pattern with case-insensitive flag
        Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(line);

        // If pattern is found in the line, print each match with its label
        while (matcher.find()) {
            System.out.println(label + " found: " + matcher.group());
        }
    }
}
