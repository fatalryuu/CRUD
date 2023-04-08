package com.example.crud.Utils;

import com.example.crud.plugins.Plugin;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomFileChooser {
    private static final Map<String, ArrayList<String>> openingExtensions = new HashMap<>();
    private static final Map<String, String> savingExtensions = new HashMap<>(
            Map.of("Text Files", "*.txt",
                    "JSON Files", "*.json",
                    "Binary Files", "*.bin")
    );

    private static void initMap(HashMap<String, Plugin> plugins) {
        for (String key : savingExtensions.keySet()) {
            ArrayList<String> extensions = new ArrayList<>();
            extensions.add(savingExtensions.get(key));
            for (String pluginKey: plugins.keySet()) {
                extensions.add("*." + plugins.get(pluginKey).getExtension());
            }
            openingExtensions.put(key, extensions);
        }
    }

    public static File getOpenFile(HashMap<String, Plugin> map) {
        initMap(map);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        for (String key : openingExtensions.keySet()) {
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(key, openingExtensions.get(key)));
        }
        try {
            return fileChooser.showOpenDialog(new Stage());
        } catch (Exception e) {
            System.err.println("Select file!");
            return null;
        }
    }

    public static File getSaveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        for (String key : savingExtensions.keySet()) {
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(key, savingExtensions.get(key))
            );
        }
        try {
            return fileChooser.showSaveDialog(new Stage());
        } catch (Exception e) {
            System.err.println("Select file!");
            return null;
        }
    }
}
