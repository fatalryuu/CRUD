package com.example.crud.factories;

import com.example.crud.MainController;
import com.example.crud.hierarchy.Gadget;
import com.example.crud.hierarchy.Tablet;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class TabletFactory implements Factory {
    private final String[] labelTexts = {"Name", "Screen size", "Year of issue", "Has Bluetooth", "Has WiFi", "OS", "Has SIM slot"};

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

    @Override
    public boolean checkInputs(ArrayList<TextField> inputs) {
        try {
            checkDefaultInputs(inputs);
            inputs.get(6).getText();
            Boolean.parseBoolean(inputs.get(7).getText());
        } catch (Exception e) {
            return false;
        }

        if (isDefaultError(inputs))
            return false;
        if (!Validations.checkIfTheOsIsCorrect(inputs.get(6).getText())) {
            MainController.errorMessage = Validations.errorMessages[6];
            return false;
        }
        if (!Validations.checkIfTheBooleanValueIsCorrect(inputs.get(7).getText())) {
            MainController.errorMessage = Validations.errorMessages[7];
            return false;
        }
        return true;
    }

    @Override
    public Gadget getGadget(ArrayList<TextField> inputs) {
        return new Tablet(inputs.get(1).getText(), Double.parseDouble(inputs.get(2).getText()),
            Integer.parseInt(inputs.get(3).getText()), Boolean.parseBoolean(inputs.get(4).getText()),
            Boolean.parseBoolean(inputs.get(5).getText()), inputs.get(6).getText(),
            Boolean.parseBoolean(inputs.get(7).getText()));
    }

    @Override
    public void putInfoToInputs(Gadget gadget, ArrayList<TextField> inputs) {
        Tablet tablet = (Tablet) gadget;
        setTextForDefaultInputs(gadget, inputs);
        inputs.get(6).setText(tablet.OS);
        inputs.get(7).setText(String.valueOf(tablet.hasSIMCardSlot));
    }
}
