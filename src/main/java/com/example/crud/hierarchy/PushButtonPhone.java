package com.example.crud.hierarchy;

import javafx.stage.Stage;

public class PushButtonPhone extends Phone {
    public int amountOfButtons;
    public PushButtonPhone(String name, double screenSize, int yearOfIssue, boolean hasBluetooth,
                           boolean hasWiFi, Camera camera, String model, int amountOfSIMCards,
                           int amountOfButtons) {
        super(name, screenSize, yearOfIssue, hasBluetooth, hasWiFi, camera, model, amountOfSIMCards);
        this.amountOfButtons = amountOfButtons;
    }
}
