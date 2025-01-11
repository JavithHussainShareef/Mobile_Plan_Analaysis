package org.example.search;

import java.util.Scanner;
import java.util.regex.*;

public class MultiValidator {

    // Helper method to get input safely
    private static String getInput(Scanner scanner1) {
        try {
            if (scanner1.hasNextLine()) {
                return scanner1.nextLine().trim();
            }
        } catch (Exception e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
        return null; // Return null if input is not available
    }

    public  void MultiValidator_Execution() {
        //Scanner scanner1 = null;
        try {
            //scanner1 = new Scanner(System.in);

            // Regular expressions
            String phoneRegex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$"; // Phone number
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"; // Email
            String urlRegex = "^(https?|ftp)://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}(/.*)?$"; // URL

            // Get phone number from user
            System.out.print("Enter a phone number: ");
            //if (scanner1.hasNextLine()) {
                //String phoneNumber = scanner1.nextLine();
                validateInput("91 6379595460", phoneRegex, "Phone Number");
//            } else {
//                System.out.println("No phone number input detected.");
//            }

            // Get email from user
            System.out.print("Enter an email address: ");
            //String email = scanner1.nextLine();
            validateInput("javithimraan@gmail.com", emailRegex, "Email Address");

            // Get URL from user
            System.out.print("Enter a URL: ");
            //String url = scanner1.nextLine();
            validateInput("https://business.bell.ca/shop/medium-large", urlRegex, "URL");

            //scanner1.close();
        } catch (Exception e) {
        System.out.println("An unexpected error occurred: " + e.getMessage());
    } finally {
//        if (scanner1 != null) {
//            scanner1.close();
//        }
    }
    }

    // Validation method
    public static void validateInput(String input, String regex, String inputType) {
        if (input.matches(regex)) {
            System.out.println(inputType + " is valid.");
        } else {
            System.out.println(inputType + " is invalid.");
        }
    }
}
