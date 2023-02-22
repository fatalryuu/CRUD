package com.company;

public abstract class Gadget {
    public double screenSize;
    public int yearOfIssue;
    public String name;
    public boolean hasBluetooth;
    public boolean hasWiFi;
    public Gadget(double screenSize, int yearOfIssue, String name, boolean hasBluetooth, boolean hasWiFi) {
        this.screenSize = screenSize;
        this.yearOfIssue = yearOfIssue;
        this.name = name;
        this.hasBluetooth = hasBluetooth;
        this.hasWiFi = hasWiFi;
    }
}
