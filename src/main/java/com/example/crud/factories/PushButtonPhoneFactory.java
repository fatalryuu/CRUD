package com.example.crud.factories;

import com.example.crud.MainController;
import com.example.crud.hierarchy.Camera;
import com.example.crud.hierarchy.Gadget;
import com.example.crud.hierarchy.PushButtonPhone;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class PushButtonPhoneFactory implements Factory {
    private final String[] labelTexts = {"Name", "Screen size", "Year of issue", "Has Bluetooth",
            "Has WiFi", "Camera(megapixels)", "Camera(zoom)", "Model", "Amount of SIM cards",
            "Amount of buttons"};
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
            Double.parseDouble(inputs.get(6).getText());
            Double.parseDouble(inputs.get(7).getText());
            inputs.get(8).getText();
            Integer.parseInt(inputs.get(9).getText());
            Integer.parseInt(inputs.get(10).getText());
        } catch (Exception e) {
            return false;
        }
        if (isDefaultError(inputs))
            return false;
        if (!Validations.checkIfTheDoubleValueIsCorrect(inputs.get(6).getText(), 24.0)) {
            MainController.errorMessage = Validations.errorMessages[6];
            return false;
        }
        if (!Validations.checkIfTheDoubleValueIsCorrect(inputs.get(7).getText(), 200.0)) {
            MainController.errorMessage = Validations.errorMessages[7];
            return false;
        }
        if (!Validations.checkIfTheIntegerValueIsCorrect(inputs.get(9).getText(), 4)) {
            MainController.errorMessage = Validations.errorMessages[9];
            return false;
        }
        if (!Validations.checkIfTheIntegerValueIsCorrect(inputs.get(10).getText(), 64)) {
            MainController.errorMessage = Validations.errorMessages[10];
            return false;
        }
        return true;
    }

    @Override
    public Gadget getGadget(ArrayList<TextField> inputs) {
        return new PushButtonPhone(inputs.get(1).getText(), Double.parseDouble(inputs.get(2).getText()),
                Integer.parseInt(inputs.get(3).getText()), Boolean.parseBoolean(inputs.get(4).getText()),
                Boolean.parseBoolean(inputs.get(5).getText()),
                new Camera(Double.parseDouble(inputs.get(6).getText()), Double.parseDouble(inputs.get(7).getText())),
                inputs.get(8).getText(), Integer.parseInt(inputs.get(9).getText()),
                Integer.parseInt(inputs.get(10).getText()));
    }

    @Override
    public void putInfoToInputs(Gadget gadget, ArrayList<TextField> inputs) {
        PushButtonPhone pushButtonPhone = (PushButtonPhone) gadget;
        setTextForDefaultInputs(gadget, inputs);
        setTextForTelephoneInputs(pushButtonPhone, inputs);
        inputs.get(10).setText(String.valueOf(pushButtonPhone.amountOfButtons));
    }
}
