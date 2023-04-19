package com.example.crud.Utils;

import com.example.crud.plugins.Plugin;
import com.example.crud.serialize.Serializer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class CustomFileChooser {
    private static final Map<String, ArrayList<String>> openingExtensions = new HashMap<>();
    private static final Map<String, String> savingExtensions = new HashMap<>();

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

    private static void initSavingMap() {
        final String PATH = "src\\main\\java\\com\\example\\crud\\serialize\\impl";
        final String PREFIX = "com.example.crud.serialize.impl.";
        try {
            File folder = new File(PATH);
            File[] files = folder.listFiles();
            for (File file : Objects.requireNonNull(files)) {
                String className = file.getName().substring(0, file.getName().lastIndexOf("."));
                Class<?> serializer = Class.forName(PREFIX + className);
                Serializer thisSerializer = (Serializer) serializer.getConstructor().newInstance();
                savingExtensions.put(thisSerializer.getName(), thisSerializer.getExtension());
            }

        } catch (Exception ignored) {

        }
    }

    public static File getOpenFile(HashMap<String, Plugin> map) {
        initSavingMap();
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
        initSavingMap();
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
