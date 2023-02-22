package com.company;

public abstract class Phone extends Gadget {
    public Camera camera;
    public String model;
    public int amountOfSIMCards;
    public Phone(double screenSize, int yearOfIssue, String name, boolean hasBluetooth,
                 boolean hasWiFi, Camera camera, String model, int amountOfSIMCards) {
        super(screenSize, yearOfIssue, name, hasBluetooth, hasWiFi);
        this.camera = camera;
        this.model = model;
        this.amountOfSIMCards = amountOfSIMCards;
    }
}
