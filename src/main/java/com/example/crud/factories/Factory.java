package com.example.crud.factories;

import com.example.crud.MainController;
import com.example.crud.Validations;
import com.example.crud.hierarchy.Camera;
import com.example.crud.hierarchy.Gadget;
import com.example.crud.hierarchy.Phone;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public interface Factory {
    default void createInstance(ArrayList<String> labelTexts, ArrayList<Control> inputs, HashMap<String, Method> mapOfSetters, HashMap<String, String> mapOfTypes, Gadget instance) {
        for (int i = 0; i < labelTexts.size(); i++) {
            try {
                switch (mapOfTypes.get(labelTexts.get(i))) {
                    case "Boolean" -> mapOfSetters.get(labelTexts.get(i))
                            .invoke(instance, ((CheckBox) inputs.get(i + 1)).isSelected());

                    case "Integer" -> mapOfSetters.get(labelTexts.get(i))
                            .invoke(instance, Integer.parseInt(((TextField) inputs.get(i + 1)).getText()));

                    case "Double" -> {
                        if (!labelTexts.get(i).startsWith("Camera"))
                            mapOfSetters.get(labelTexts.get(i)).invoke(instance, Double.parseDouble(((TextField) inputs.get(i + 1)).getText()));
                        else {
                            mapOfSetters.get("Camera").invoke(instance,
                                    new Camera(Double.parseDouble(((TextField) inputs.get(i + 1)).getText()),
                                            Double.parseDouble(((TextField) inputs.get(i + 2)).getText())));
                            i++;
                        }
                    }

                    case "String" -> mapOfSetters.get(labelTexts.get(i))
                            .invoke(instance, ((TextField) inputs.get(i + 1)).getText());
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
    void configureLabelsAndInputs(HBox container);
    boolean checkInputs();
    Gadget getGadget();
    ArrayList<Control> getInputs();
    void putInfoToInputs(Gadget gadget, ArrayList<Label> labels);
}
