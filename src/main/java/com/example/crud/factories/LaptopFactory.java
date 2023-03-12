package com.example.crud.factories;

import com.example.crud.MainController;
import com.example.crud.hierarchy.Gadget;
import com.example.crud.hierarchy.Laptop;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class LaptopFactory implements Factory {
    private final String[] labelTexts = {"Name", "Screen size", "Year of issue", "Has Bluetooth", "Has WiFi", "OS", "Memory(GB)", "Has touchpad"};

    @Override
    public void renameLabels(ArrayList<Label> labels) {
        for (int i = 0; i < labelTexts.length; i++) {
            labels.get(i + 1).setText(labelTexts[i]);
        }
    }

    @Override
    public int getAmountOfFields() {
        return labelTexts.length;
    }

    @Override
    public void showLabelsAndInputs(ArrayList<Label> labels, ArrayList<TextField> inputs) {
        for (int i = 0; i < labels.size(); i++) {
            labels.get(i).setVisible(i < labelTexts.length + 1);
            inputs.get(i).setVisible(i < labelTexts.length + 1);
        }
    }

    public static void createAlert(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    @Override
    public boolean checkInputs(ArrayList<TextField> inputs) {
        try {
            checkDefaultInputs(inputs);
            Integer.parseInt(inputs.get(7).getText());
            Boolean.parseBoolean(inputs.get(8).getText());
        } catch (Exception e) {
            return false;
        }

        if (isDefaultError(inputs))
            return false;
        if (!Validations.checkIfTheOsIsCorrect(inputs.get(6).getText())) {
            MainController.errorMessage = Validations.errorMessages[6];
            return false;
        }
        if (!Validations.checkIfTheIntegerValueIsCorrect(inputs.get(7).getText(), 512)) {
            MainController.errorMessage = Validations.errorMessages[7];
            return false;
        }
        if (!Validations.checkIfTheBooleanValueIsCorrect(inputs.get(8).getText())) {
            MainController.errorMessage = Validations.errorMessages[8];
            return false;
        }
        return true;
    }

    @Override
    public Gadget getGadget(ArrayList<TextField> inputs) {
        return new Laptop(inputs.get(1).getText(), Double.parseDouble(inputs.get(2).getText()),
                Integer.parseInt(inputs.get(3).getText()), Boolean.parseBoolean(inputs.get(4).getText()),
                Boolean.parseBoolean(inputs.get(5).getText()), inputs.get(6).getText(),
                Integer.parseInt(inputs.get(7).getText()), Boolean.parseBoolean(inputs.get(8).getText()));
    }

    @Override
    public void putInfoToInputs(Gadget gadget, ArrayList<TextField> inputs) {
        Laptop laptop = (Laptop) gadget;
        setTextForDefaultInputs(gadget, inputs);
        inputs.get(6).setText(laptop.OS);
        inputs.get(7).setText(String.valueOf(laptop.memory));
        inputs.get(8).setText(String.valueOf(laptop.hasTouchpad));
    }
}
