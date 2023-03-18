package com.example.crud.factories;

import com.example.crud.GUI;
import com.example.crud.Maps;
import com.example.crud.Name;
import com.example.crud.Validations;
import com.example.crud.hierarchy.Gadget;
import com.example.crud.hierarchy.PushButtonPhone;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PushButtonPhoneFactory implements Factory {
    private final ArrayList<String> labelTexts;
    private static ArrayList<Control> inputs;

    public PushButtonPhoneFactory() {
        labelTexts = new ArrayList<>();
        Class<?> pushButtonPhoneClass = PushButtonPhone.class;
        for (Method method : Arrays.stream(pushButtonPhoneClass.getMethods()).filter(x -> x.getName().startsWith("get")).toList()) {
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
        return Validations.checkInputs(inputs, labelTexts, PushButtonPhone.class);
    }

    @Override
    public Gadget getGadget() {
        PushButtonPhone pushButtonPhone = new PushButtonPhone();
        HashMap<String, Method> mapOfSetters = Maps.getMapOfSettersOrGetters("set", PushButtonPhone.class);
        HashMap<String, String> mapOfTypes = Maps.getMapOfTypes(PushButtonPhone.class);
        createInstance(labelTexts, inputs, mapOfSetters, mapOfTypes, pushButtonPhone);
        return pushButtonPhone;
    }

    @Override
    public ArrayList<Control> getInputs() {
        return inputs;
    }

    @Override
    public void putInfoToInputs(Gadget gadget, ArrayList<Label> labels) {
        PushButtonPhone pushButtonPhone = (PushButtonPhone) gadget;
        HashMap<String, Method> map = Maps.getMapOfSettersOrGetters("get", PushButtonPhone.class);
        GUI.putInfoToInputs(labels, inputs, map, pushButtonPhone);
    }
}
