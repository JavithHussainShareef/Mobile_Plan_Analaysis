package org.example.utils;

import java.util.regex.Pattern;

public class RegexValidator {
    public boolean validate(String input) {
        String urlRegex = "^(http|https)://.*$";
        String emailRegex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

        if (Pattern.matches(urlRegex, input)) {
            return true; // Valid URL
        } else if (Pattern.matches(emailRegex, input)) {
            return true; // Valid Email
        } else {
            return false; // Invalid input
        }
    }

    public boolean findPattern(String content, String pattern) {
        return Pattern.compile(pattern).matcher(content).find();
    }
}
