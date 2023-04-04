package com.example.crud;

import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;

public class Validations {
    private static void recolorInput(TextField input, String color) {
        input.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: " + color + "; -fx-border-radius: 3");
    }

    public static boolean checkIfTheStringValueIsCorrect(TextField input) {
        boolean isCorrect = input.getText().matches("^[a-zA-Z]+[a-zA-Z0-9\\s-_]*$");
        if (!isCorrect)
            recolorInput(input, "#ff0000");
        else
            recolorInput(input, "#00ffc2");
        return isCorrect;
    }

    public static boolean checkIfTheIntegerValueIsCorrect(TextField input, Integer max) {
        boolean isCorrect;
        try {
            isCorrect = Integer.parseInt(input.getText()) > 0 && Integer.parseInt(input.getText()) <= max;
        } catch (NumberFormatException e) {
            isCorrect = false;
        }
        if (!isCorrect)
            recolorInput(input, "#ff0000");
        else
            recolorInput(input, "#00ffc2");
        return isCorrect;
    }

    public static boolean checkIfTheDoubleValueIsCorrect(TextField input, Double max) {
        boolean isCorrect;
        try {
            isCorrect = Double.parseDouble(input.getText()) > 0 && Double.parseDouble(input.getText()) <= max;
        } catch (NumberFormatException e) {
            isCorrect = false;
        }
        if (!isCorrect)
            recolorInput(input, "#ff0000");
        else
            recolorInput(input, "#00ffc2");
        return isCorrect;
    }

    public static boolean checkIfInstanceNameIsCorrect(TextField input) {
        boolean isCorrect = input.getText().matches("^[a-zA-Z]+[a-zA-Z0-9]*$");
        if (!isCorrect)
            recolorInput(input, "#ff0000");
        return isCorrect;
    }

    public static boolean checkIfTheYearIsCorrect(TextField input) {
        boolean isCorrect;
        try {
            isCorrect = Integer.parseInt(input.getText()) >= 1900 && Integer.parseInt(input.getText()) <= 2023;
        } catch (NumberFormatException e) {
            isCorrect = false;
        }
        if (!isCorrect)
            recolorInput(input, "#ff0000");
        else
            recolorInput(input, "#00ffc2");
        return isCorrect;
    }

    public static boolean checkIfTheOsIsCorrect(TextField input) {
        boolean isCorrect = input.getText().matches("^[a-zA-Z]+$");
        if (!isCorrect)
            recolorInput(input, "#ff0000");
        else
            recolorInput(input, "#00ffc2");
        return isCorrect;
    }

    public static boolean checkIfModelIsCorrect(TextField input) {
        boolean isCorrect = input.getText().matches("^[a-zA-Z0-9\\s]+$");
        if (!isCorrect)
            recolorInput(input, "#ff0000");
        else
            recolorInput(input, "#00ffc2");
        return isCorrect;
    }

    public static boolean checkIfTheNetworkIsCorrect(TextField input) {
        boolean isCorrect = input.getText().matches("^[1-5]G$");
        if (!isCorrect)
            recolorInput(input, "#ff0000");
        else
            recolorInput(input, "#00ffc2");
        return isCorrect;
    }

    public static boolean checkInputs(ArrayList<Control> inputs, ArrayList<String> labelsTexts, Class thisClass) {
        HashMap<String, String> map = Maps.getMapOfTypes(thisClass);
        boolean[] isCorrects = new boolean[map.size() + 1];
        for (int i = 0; i < map.size(); i++) {
            isCorrects[i] = true;
        }
        isCorrects[0] = true;//checkIfInstanceNameIsCorrect((TextField) inputs.get(0)); //instance name
        for (int i = 1; i < labelsTexts.size() + 1; i++) {
            switch (map.get(labelsTexts.get(i - 1))) {
                case "String" -> {
                    switch (labelsTexts.get(i - 1)) {
                        case "Name" -> isCorrects[i] = checkIfTheStringValueIsCorrect((TextField) inputs.get(i));
                        case "OS" -> isCorrects[i] = checkIfTheOsIsCorrect((TextField) inputs.get(i));
                        case "Supported network" -> isCorrects[i] = checkIfTheNetworkIsCorrect((TextField) inputs.get(i));
                        case "Model" -> isCorrects[i] = checkIfModelIsCorrect((TextField) inputs.get(i));
                    }
                }
                case "Integer" -> {
                    switch (labelsTexts.get(i - 1)) {
                        case "Year of issue" -> isCorrects[i] = checkIfTheYearIsCorrect((TextField) inputs.get(i));
                        case "Memory(GB)" -> isCorrects[i] = checkIfTheIntegerValueIsCorrect((TextField) inputs.get(i), 1024);
                        case "Amount of SIM" -> isCorrects[i] = checkIfTheIntegerValueIsCorrect((TextField) inputs.get(i), 4);
                        case "Amount of buttons" -> isCorrects[i] = checkIfTheIntegerValueIsCorrect((TextField) inputs.get(i), 64);
                    }
                }
                case "Double" -> {
                    switch (labelsTexts.get(i - 1)) {
                        case "Screen size" -> isCorrects[i] = checkIfTheDoubleValueIsCorrect((TextField) inputs.get(i), 50.0);
                        case "Camera megapixels" -> isCorrects[i] = checkIfTheDoubleValueIsCorrect((TextField) inputs.get(i), 128.0);
                        case "Camera zoom" -> isCorrects[i] = checkIfTheDoubleValueIsCorrect((TextField) inputs.get(i), 1000.0);
                    }
                }
            }
        }

        for (int i = 0; i < map.size(); i++) {
            if (!isCorrects[i]) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect input!");
                alert.setContentText("Check red input(-s).");
                alert.showAndWait();
                return false;
            }
        }

        return true;
    }
}
