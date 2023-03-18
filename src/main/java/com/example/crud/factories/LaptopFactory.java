package com.example.crud.factories;

import com.example.crud.GUI;
import com.example.crud.Maps;
import com.example.crud.Name;
import com.example.crud.Validations;
import com.example.crud.hierarchy.Gadget;
import com.example.crud.hierarchy.Laptop;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LaptopFactory implements Factory {
    private final ArrayList<String> labelTexts;
    private static ArrayList<Control> inputs;

    public LaptopFactory() {
        labelTexts = new ArrayList<>();
        Class<?> laptopClass = Laptop.class;
        for (Method method : Arrays.stream(laptopClass.getMethods()).filter(x -> x.getName().startsWith("get")).toList()) {
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
        return Validations.checkInputs(inputs, labelTexts, Laptop.class);
    }

    @Override
    public Gadget getGadget() {
        Laptop laptop = new Laptop();
        HashMap<String, Method> mapOfSetters = Maps.getMapOfSettersOrGetters("set", Laptop.class);
        HashMap<String, String> mapOfTypes = Maps.getMapOfTypes(Laptop.class);
        createInstance(labelTexts, inputs, mapOfSetters, mapOfTypes, laptop);
        return laptop;
    }

    @Override
    public ArrayList<Control> getInputs() {
        return inputs;
    }

    @Override
    public void putInfoToInputs(Gadget gadget, ArrayList<Label> labels) {
        Laptop laptop = (Laptop) gadget;
        HashMap<String, Method> map = Maps.getMapOfSettersOrGetters("get", Laptop.class);
        GUI.putInfoToInputs(labels, inputs, map, laptop);
    }
}
