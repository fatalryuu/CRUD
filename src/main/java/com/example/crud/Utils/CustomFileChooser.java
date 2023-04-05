package com.example.crud.Utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomFileChooser {
    private static final Map<String, String> availableExtensions = new HashMap<>(
            Map.of("Text Files", "*.txt",
                    "JSON Files", "*.json",
                    "Binary Files", "*.bin")
    );
    public static String getFilePathOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        ArrayList<String> extensions = new ArrayList<>();
        for (String key : availableExtensions.keySet()) {
            extensions.add(availableExtensions.get(key));
        }
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Available extensions", extensions));
        try {
            File fileObject = fileChooser.showOpenDialog(new Stage());
            return fileObject.getPath();
        } catch (Exception e) {
            System.err.println("Select file!");
            return "";
        }
    }

    public static String getFilePathSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        for (String key : availableExtensions.keySet()) {
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(key, availableExtensions.get(key))
            );
        }
        try {
            File fileObject = fileChooser.showSaveDialog(new Stage());
            return fileObject.getPath();
        } catch (Exception e) {
            System.err.println("Select file!");
            return "";
        }
    }
}
