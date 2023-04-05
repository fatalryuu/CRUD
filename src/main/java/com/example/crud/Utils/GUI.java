package com.example.crud.Utils;

import com.example.crud.hierarchy.Camera;
import com.example.crud.hierarchy.Gadget;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GUI {
    private static void renameLabels(VBox LabelsVBox, ArrayList<String> labelTexts) {
        Label label = (Label) LabelsVBox.getChildren().get(0);
        label.setText("Name of instance");
        for (int i = 0; i < labelTexts.size(); i++) {
            label = (Label) LabelsVBox.getChildren().get(i + 1);
            label.setText(labelTexts.get(i));
        }
    }

    private static void configureCameraLabelsAndInputs(ArrayList<Control> inputs, VBox InputsVBox, VBox LabelsVBox, ArrayList<String> labelTexts, int index) {
        TextField megapixelsInput = new TextField();
        TextField zoomInput = new TextField();
        inputs.add(megapixelsInput);
        inputs.add(zoomInput);
        InputsVBox.getChildren().add(megapixelsInput);
        InputsVBox.getChildren().add(zoomInput);
        Label additionalLabel = new Label();
        LabelsVBox.getChildren().add(additionalLabel);
        additionalLabel.setStyle("-fx-font-size: 16px");
        Class<?> camera = Camera.class;
        ArrayList<String> newLabelsTexts = new ArrayList<>();
        for (int j = 0; j < labelTexts.size(); j++) {
            if (j != index - 1)
                newLabelsTexts.add(labelTexts.get(j));
            else {
                for (Method method : Arrays.stream(camera.getMethods()).filter(x -> x.getName().startsWith("get")).toList()) {
                    if (method.isAnnotationPresent(Name.class)) {
                        newLabelsTexts.add(method.getAnnotation(Name.class).value());
                    }
                }
            }
        }
        for (int j = 0; j < newLabelsTexts.size(); j++) {
            if (j < labelTexts.size())
                labelTexts.set(j, newLabelsTexts.get(j));
            else
                labelTexts.add(newLabelsTexts.get(j));
        }
    }
    public static ArrayList<Control> createLabelsAndInputs(HBox container, ArrayList<String> labelTexts) {
        ArrayList<Control> inputs = new ArrayList<>();
        VBox LabelsVBox = (VBox) container.getChildren().get(0);
        VBox InputsVBox = (VBox) container.getChildren().get(1);
        LabelsVBox.setSpacing(5);
        InputsVBox.setSpacing(5);
        LabelsVBox.getChildren().clear();
        InputsVBox.getChildren().clear();
        int i = 0;
        while (i < labelTexts.size() + 1) {
            Label label = new Label();
            if (i > 0 && labelTexts.get(i - 1).startsWith("Has")) {
                CheckBox checkBox = new CheckBox();
                inputs.add(checkBox);
                checkBox.setStyle("-fx-padding: 4px");
                InputsVBox.getChildren().add(checkBox);
            } else if (i > 0 && labelTexts.get(i - 1).equals("Camera")) {
                configureCameraLabelsAndInputs(inputs, InputsVBox, LabelsVBox, labelTexts, i);
                i++;
            } else {
                TextField input = new TextField();
                inputs.add(input);
                InputsVBox.getChildren().add(input);
            }
            label.setStyle("-fx-font-size: 16px");
            LabelsVBox.getChildren().add(label);
            i++;
        }
        renameLabels(LabelsVBox, labelTexts);
        return inputs;
    }
    public static void clearInputs(ArrayList<Control> inputs, ArrayList<Label> labels) {
        for (int i = 0; i < inputs.size(); i++) {
            if (labels.get(i).getText().startsWith("Has")) {
                CheckBox checkBox = (CheckBox) inputs.get(i);
                checkBox.setSelected(false);
            } else {
                TextField textField = (TextField) inputs.get(i);
                textField.setText("");
                textField.setStyle("");
            }
        }
    }
    public static void putInfoToInputs(ArrayList<Label> labels, ArrayList<Control> inputs, HashMap<String, Method> map, Gadget instance) {
        for (int i = 0; i < labels.size() - 1; i++) {
            try {
                if (labels.get(i + 1).getText().startsWith("Has")) {
                    ((CheckBox) inputs.get(i + 1)).setSelected((Boolean) map.get(labels.get(i + 1).getText()).invoke(instance));
                } else {
                    if (!labels.get(i + 1).getText().startsWith("Camera"))
                        ((TextField) inputs.get(i + 1)).setText(String.valueOf(map.get(labels.get(i + 1).getText()).invoke(instance)));
                    else {
                        Camera camera = (Camera) map.get("Camera").invoke(instance);
                        ((TextField) inputs.get(i + 1)).setText(String.valueOf(camera.megapixels));
                        ((TextField) inputs.get(i + 2)).setText(String.valueOf(camera.zoom));
                        i++;
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
