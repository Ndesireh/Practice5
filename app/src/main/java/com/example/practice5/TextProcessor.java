package com.example.practice5;

public class TextProcessor {

    public static String processMunicipality(String municipality) {

        if (municipality == null) {
            return null;
        }

        String input = municipality.trim();

        if (input.isEmpty()) {
            return null;
        }

        if ("Beijing".equalsIgnoreCase(input)) {
            return "Beijing";
        }

        if ("Shanghai".equalsIgnoreCase(input)) {
            return "Shanghai";
        }

        if ("Chongqing".equalsIgnoreCase(input)) {
            return "Chongqing";
        }

        if ("Tianjin".equalsIgnoreCase(input)) {
            return "Tianjin";
        }

        return null;
    }
}