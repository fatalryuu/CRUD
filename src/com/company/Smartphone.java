package com.company;

public class Smartphone extends Phone {
    public String supportedNetwork; //2G, 3G
    public boolean hasFingerprintScanner;
    public String OS;
    public Smartphone(double screenSize, int yearOfIssue, String name, boolean hasBluetooth,
                      boolean hasWiFi, Camera camera, String model, int amountOfSIMCards,
                      String supportedNetwork, boolean hasFingerprintScanner, String OS) {
        super(screenSize, yearOfIssue, name, hasBluetooth, hasWiFi, camera, model, amountOfSIMCards);
        this.supportedNetwork = supportedNetwork;
        this.hasFingerprintScanner = hasFingerprintScanner;
        this.OS = OS;
    }
}
