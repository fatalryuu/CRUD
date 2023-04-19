package com.example.crud.serialize.impl;

import com.example.crud.Utils.Maps;
import com.example.crud.hierarchy.Gadget;
import com.example.crud.serialize.Serializer;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.crud.MainController.createAlert;

public class TextSerializer implements Serializer {

    private final String ARROW = " -> ";
    private final String TAB = "    ";
    private final String CLASS_NAME_PREFIX = "com.example.crud.hierarchy.";

    public TextSerializer() {

    }

    private String getClassFieldsString(Object obj) throws InvocationTargetException, IllegalAccessException {
        StringBuilder result = new StringBuilder();
        HashMap<String, Method> map = Maps.getMapOfSettersOrGetters("get", obj.getClass());
        for (String key : map.keySet()) {
            result.append(TAB + TAB).append(key).append(ARROW).append(map.get(key).invoke(obj)).append("\n");
        }
        return result.toString();
    }

    @Override
    public String getName() {
        return "Text Files";
    }

    @Override
    public String getExtension() {
        return "*.txt";
    }

    @Override
    public void serialize(ArrayList<Gadget> gadgets, String path) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            for (Gadget gadget : gadgets) {
                String fullClassName = gadget.getClass().getSimpleName();
                bufferedWriter.write(fullClassName + ARROW + "{\n");
                HashMap<String, Method> map = Maps.getMapOfSettersOrGetters("get", gadget.getClass());
                for (String key : map.keySet()) {
                    String returnType = map.get(key).getReturnType().getSimpleName();
                    if (returnType.equals("int") || returnType.equals("boolean") || returnType.equals("double") || returnType.equals("String")) {
                        String value = String.valueOf(map.get(key).invoke(gadget));
                        if (value.contains("{")) {
                            value = value.substring(0, value.indexOf("{")) + "/" + value.substring(value.indexOf("{"));
                        }
                        bufferedWriter.write(TAB + key + ARROW + value + "\n");
                    } else {
                        bufferedWriter.write(TAB + key + ARROW + "{\n");
                        bufferedWriter.write(getClassFieldsString(map.get(key).invoke(gadget)));
                        bufferedWriter.write(TAB + "}\n");
                    }
                }
                bufferedWriter.write("}\n");
            }
        } catch (Exception e) {
            createAlert(Alert.AlertType.ERROR, "File error", "Error while text file serialization!", "Check file info");
        }
    }

    private String getStringBeforeArrow(String str) {
        String substr = "";
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i + 1] != '-') {
                substr += chars[i];
            } else {
                break;
            }
        }
        return substr.trim();
    }

    private String getStringAfterArrow(String str) {
        String substr = "";
        char[] chars = str.toCharArray();
        for (int i = str.indexOf('>') + 1; i < chars.length; i++) {
            substr += chars[i];
        }
        return substr.trim();
    }

    @Override
    public ArrayList<Gadget> deserialize(String path) {
        ArrayList<Gadget> gadgets = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String str = bufferedReader.readLine();
            ArrayList<String> classNames = new ArrayList<>();
            Object instance = null;
            Object subInstance = null;
            HashMap<String, Method> map = new HashMap<>();
            HashMap<String, String> mapOfTypes = new HashMap<>();
            while (str != null) {
                str = str.trim();
                if (str.endsWith("{") && !str.endsWith("/{")) {
                    classNames.add(getStringBeforeArrow(str));
                    Class<?> clazz = Class.forName(CLASS_NAME_PREFIX + classNames.get(classNames.size() - 1));
                    if (instance == null) {
                        instance = clazz.getConstructor().newInstance();
                    } else {
                        subInstance = clazz.getConstructor().newInstance();
                    }
                    map = Maps.getMapOfSettersOrGetters("set", clazz);
                    mapOfTypes = Maps.getMapOfTypes(clazz);
                } else if (str.equals("}")) {
                    if (subInstance != null) {
                        map = Maps.getMapOfSettersOrGetters("set", instance.getClass());
                        mapOfTypes = Maps.getMapOfTypes(instance.getClass());
                        map.get(subInstance.getClass().getSimpleName()).invoke(instance, subInstance);
                        subInstance = null;
                    }
                    classNames.remove(classNames.size() - 1);
                    if (classNames.isEmpty()) {
                        gadgets.add((Gadget) instance);
                        instance = null;
                    }
                } else {
                    String field = getStringBeforeArrow(str);
                    String value = getStringAfterArrow(str);
                    switch (mapOfTypes.get(field)) {
                        case "Integer" -> map.get(field)
                                .invoke(subInstance != null ? subInstance : instance, Integer.parseInt(value));

                        case "String" -> {
                            if (value.contains("/{")) {
                                value = value.substring(0, value.indexOf("/")) + value.substring(value.indexOf("{"));
                            }
                            map.get(field)
                                    .invoke(subInstance != null ? subInstance : instance, value);
                        }
                        case "Boolean" -> map.get(field)
                                .invoke(subInstance != null ? subInstance : instance, Boolean.parseBoolean(value));

                        case "Double" -> map.get(field)
                                .invoke(subInstance != null ? subInstance : instance, Double.parseDouble(value));
                    }
                }
                str = bufferedReader.readLine();
            }
        } catch (Exception e) {
            createAlert(Alert.AlertType.ERROR, "File error", "Error while text file deserialization!", "Check file info");
        }
        return gadgets;
    }
}
