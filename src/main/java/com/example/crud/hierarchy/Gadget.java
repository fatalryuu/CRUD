package com.example.crud.hierarchy;

public abstract class Gadget {
    public String name;
    public double screenSize;
    public int yearOfIssue;
    public boolean hasBluetooth;
    public boolean hasWiFi;
    public Gadget(String name, double screenSize, int yearOfIssue, boolean hasBluetooth, boolean hasWiFi) {
        this.screenSize = screenSize;
        this.yearOfIssue = yearOfIssue;
        this.name = name;
        this.hasBluetooth = hasBluetooth;
        this.hasWiFi = hasWiFi;
    }
    public Gadget() {

    }
}
