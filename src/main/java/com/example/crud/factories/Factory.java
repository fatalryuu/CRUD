package com.example.crud.factories;

import com.example.crud.MainController;
import com.example.crud.hierarchy.Gadget;
import com.example.crud.hierarchy.Phone;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public interface Factory {
    default void checkDefaultInputs(ArrayList<TextField> inputs) {
        Double.parseDouble(inputs.get(2).getText());
        Integer.parseInt(inputs.get(3).getText());
        Boolean.parseBoolean(inputs.get(4).getText());
        Boolean.parseBoolean(inputs.get(5).getText());
    }

    default void setTextForDefaultInputs(Gadget gadget, ArrayList<TextField> inputs) {
        inputs.get(1).setText(gadget.name);
        inputs.get(2).setText(String.valueOf(gadget.screenSize));
        inputs.get(3).setText(String.valueOf(gadget.yearOfIssue));
        inputs.get(4).setText(String.valueOf(gadget.hasBluetooth));
        inputs.get(5).setText(String.valueOf(gadget.hasWiFi));
    }

    default void setTextForTelephoneInputs(Phone phone, ArrayList<TextField> inputs) {
        inputs.get(6).setText(String.valueOf(phone.camera.megapixels));
        inputs.get(7).setText(String.valueOf(phone.camera.zoom));
        inputs.get(8).setText(phone.model);
        inputs.get(9).setText(String.valueOf(phone.amountOfSIMCards));
    }

    default boolean isDefaultError(ArrayList<TextField> inputs) {
        Validations.initErrorsMessages();
        if (!Validations.checkIfInstanceNameIsCorrect(inputs.get(0).getText())) {
            MainController.errorMessage = Validations.errorMessages[0];
            return true;
        }
        if (!Validations.checkIfTheDoubleValueIsCorrect(inputs.get(2).getText(), 50.0)) {
            MainController.errorMessage = Validations.errorMessages[2];
            return true;
        }
        if (!Validations.checkIfTheYearIsCorrect(inputs.get(3).getText())) {
            MainController.errorMessage = Validations.errorMessages[3];
            return true;
        }
        if (!Validations.checkIfTheBooleanValueIsCorrect(inputs.get(4).getText())) {
            MainController.errorMessage = Validations.errorMessages[4];
            return true;
        }
        if (!Validations.checkIfTheBooleanValueIsCorrect(inputs.get(5).getText())) {
            MainController.errorMessage = Validations.errorMessages[5];
            return true;
        }
        return false;
    }

    void renameLabels(ArrayList<Label> labels);
    int getAmountOfFields();
    void showLabelsAndInputs(ArrayList<Label> labels, ArrayList<TextField> inputs);
    boolean checkInputs(ArrayList<TextField> inputs);
    Gadget getGadget(ArrayList<TextField> inputs);
    void putInfoToInputs(Gadget gadget, ArrayList<TextField> inputs);
}
