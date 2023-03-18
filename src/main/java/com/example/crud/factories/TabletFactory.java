package com.example.crud.factories;

import com.example.crud.GUI;
import com.example.crud.Maps;
import com.example.crud.Name;
import com.example.crud.Validations;
import com.example.crud.hierarchy.Gadget;
import com.example.crud.hierarchy.Tablet;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TabletFactory implements Factory {
    private final ArrayList<String> labelTexts;
    private static ArrayList<Control> inputs;
    public TabletFactory() {
        labelTexts = new ArrayList<>();
        Class<?> tabletClass = Tablet.class;
        for (Method method : Arrays.stream(tabletClass.getMethods()).filter(x -> x.getName().startsWith("get")).toList()) {
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
        return Validations.checkInputs(inputs, labelTexts, Tablet.class);
    }

    @Override
    public Gadget getGadget() {
        Tablet tablet = new Tablet();
        HashMap<String, Method> mapOfSetters = Maps.getMapOfSettersOrGetters("set", Tablet.class);
        HashMap<String, String> mapOfTypes = Maps.getMapOfTypes(Tablet.class);
        createInstance(labelTexts, inputs, mapOfSetters, mapOfTypes, tablet);
        return tablet;
    }

    @Override
    public ArrayList<Control> getInputs() {
        return inputs;
    }

    @Override
    public void putInfoToInputs(Gadget gadget, ArrayList<Label> labels) {
        Tablet tablet = (Tablet) gadget;
        HashMap<String, Method> map = Maps.getMapOfSettersOrGetters("get", Tablet.class);
        GUI.putInfoToInputs(labels, inputs, map, tablet);
    }
}
