package com.company;

public class PushButtonPhone extends Phone {
    public int amountOfButtons;
    public PushButtonPhone(double screenSize, int yearOfIssue, String name, boolean hasBluetooth,
                           boolean hasWiFi, Camera camera, String model, int amountOfSIMCards,
                           int amountOfButtons) {
        super(screenSize, yearOfIssue, name, hasBluetooth, hasWiFi, camera, model, amountOfSIMCards);
        this.amountOfButtons = amountOfButtons;
    }
}
