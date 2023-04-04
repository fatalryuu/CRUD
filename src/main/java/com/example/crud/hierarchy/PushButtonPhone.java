package com.example.crud.hierarchy;

import com.example.crud.Name;
import com.example.crud.Type;
import javafx.stage.Stage;

import java.io.Serializable;

public class PushButtonPhone extends Phone implements Serializable {
    public int amountOfButtons;
    public PushButtonPhone(String name, double screenSize, int yearOfIssue, boolean hasBluetooth,
                           boolean hasWiFi, Camera camera, String model, int amountOfSIMCards,
                           int amountOfButtons) {
        super(name, screenSize, yearOfIssue, hasBluetooth, hasWiFi, camera, model, amountOfSIMCards);
        this.amountOfButtons = amountOfButtons;
    }
    public PushButtonPhone() {

    }

    @Name("Amount of buttons")
    @Type("Integer")
    public int getAmountOfButtons() {
        return amountOfButtons;
    }

    @Name("Amount of buttons")
    public void setAmountOfButtons(int amountOfButtons) {
        this.amountOfButtons = amountOfButtons;
    }
}
