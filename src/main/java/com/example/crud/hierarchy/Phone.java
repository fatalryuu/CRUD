package com.example.crud.hierarchy;

public abstract class Phone extends Gadget {
    public Camera camera;
    public String model;
    public int amountOfSIMCards;
    public Phone(String name, double screenSize, int yearOfIssue, boolean hasBluetooth,
                 boolean hasWiFi, Camera camera, String model, int amountOfSIMCards) {
        super(name, screenSize, yearOfIssue, hasBluetooth, hasWiFi);
        this.camera = camera;
        this.model = model;
        this.amountOfSIMCards = amountOfSIMCards;
    }

    public Phone() {

    }
}
