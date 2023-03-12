package com.example.crud.factories;

public class Validations {
    private static final int MAX_INPUTS = 12;
    public static String[] errorMessages = new String[MAX_INPUTS];

    public static void initErrorsMessages() {
        for (int i = 0; i < MAX_INPUTS; i++) {
            errorMessages[i] = "Check the " + (i + 1) + " input";
        }
    }

    public static boolean checkIfStringIsNotNull(String str) {
        return !str.equals("");
    }

    public static boolean checkIfInstanceNameIsCorrect(String str) {
        return str.matches("^[a-zA-Z]+[a-zA-Z0-9]*$");
    }

    public static boolean checkIfTheIntegerValueIsCorrect(String str, Integer max) {
        return (Integer.parseInt(str) > 0 && Integer.parseInt(str) <= max);
    }

    public static boolean checkIfTheDoubleValueIsCorrect(String str, Double max) {
        return (Double.parseDouble(str) > 0 && Double.parseDouble(str) <= max);
    }

    public static boolean checkIfTheYearIsCorrect(String str) {
        return (Integer.parseInt(str) > 1900 && Integer.parseInt(str) < 2023);
    }

    public static boolean checkIfTheBooleanValueIsCorrect(String str) {
        return (str.equals("true") || str.equals("false"));
    }

    public static boolean checkIfTheOsIsCorrect(String str) {
        return str.matches("^[a-zA-Z]+$");
    }

    public static boolean checkIfTheNetworkIsCorrect(String str) {
        return str.matches("^[1-5]G$");
    }
}
