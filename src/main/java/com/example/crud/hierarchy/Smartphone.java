package com.example.crud.hierarchy;

import javafx.stage.Stage;

public class Smartphone extends Phone {
    public String supportedNetwork; //2G, 3G
    public String OS;
    public Smartphone(String name, double screenSize, int yearOfIssue, boolean hasBluetooth,
                      boolean hasWiFi, Camera camera, String model, int amountOfSIMCards,
                      String OS, String supportedNetwork) {
        super(name, screenSize, yearOfIssue,  hasBluetooth, hasWiFi, camera, model, amountOfSIMCards);
        this.supportedNetwork = supportedNetwork;
        this.OS = OS;
    }
}
