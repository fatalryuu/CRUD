package com.example.crud.factories;

import com.example.crud.GUI;
import com.example.crud.Maps;
import com.example.crud.Name;
import com.example.crud.Validations;
import com.example.crud.hierarchy.Gadget;
import com.example.crud.hierarchy.Smartphone;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SmartphoneFactory implements Factory {
    private final ArrayList<String> labelTexts;
    private static ArrayList<Control> inputs;

    public SmartphoneFactory() {
        labelTexts = new ArrayList<>();
        Class<?> smartphoneClass = Smartphone.class;
        for (Method method : Arrays.stream(smartphoneClass.getMethods()).filter(x -> x.getName().startsWith("get")).toList()) {
            if (method.isAnnotationPresent(Name.class))
                labelTexts.add(method.getAnnotation(Name.class).value());
        }
    }

    @Override
    public void configureLabelsAndInputs(HBox container) {
        inputs = GUI.createLabelsAndInputs(container, labelTexts);
    }

    @Override
    public boolean checkInputs() {
        return Validations.checkInputs(inputs, labelTexts, Smartphone.class);
    }

    @Override
    public Gadget getGadget() {
        Smartphone smartphone = new Smartphone();
        HashMap<String, Method> mapOfSetters = Maps.getMapOfSettersOrGetters("set", Smartphone.class);
        HashMap<String, String> mapOfTypes = Maps.getMapOfTypes(Smartphone.class);
        createInstance(labelTexts, inputs, mapOfSetters, mapOfTypes, smartphone);
        return smartphone;
    }

    @Override
    public ArrayList<Control> getInputs() {
        return inputs;
    }

    @Override
    public void putInfoToInputs(Gadget gadget, ArrayList<Label> labels) {
        Smartphone smartphone = (Smartphone) gadget;
        HashMap<String, Method> map = Maps.getMapOfSettersOrGetters("get", Smartphone.class);
        GUI.putInfoToInputs(labels, inputs, map, smartphone);
    }
}
