package com.example.crud.hierarchy;

import com.example.crud.Name;
import com.example.crud.Type;

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

    @Name("Camera")
    @Type("Camera")
    public Camera getCamera() {
        return camera;
    }

    @Name("Camera")
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Name("Model")
    @Type("String")
    public String getModel() {
        return model;
    }

    @Name("Model")
    public void setModel(String model) {
        this.model = model;
    }

    @Name("Amount of SIM")
    @Type("Integer")
    public int getAmountOfSIMCards() {
        return amountOfSIMCards;
    }

    @Name("Amount of SIM")
    public void setAmountOfSIMCards(int amountOfSIMCards) {
        this.amountOfSIMCards = amountOfSIMCards;
    }
}
