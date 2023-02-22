package com.company;

public class Tablet extends Gadget {
    public String OS;
    public boolean hasSIMCardSLot;
    public Tablet(double screenSize, int yearOfIssue, String name, boolean hasBluetooth,
                  boolean hasWiFi, String OS, boolean hasSIMCardSLot) {
        super(screenSize, yearOfIssue, name, hasBluetooth, hasWiFi);
        this.OS = OS;
        this.hasSIMCardSLot = hasSIMCardSLot;
    }
}
