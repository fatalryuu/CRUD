package com.company;

public class Tablet extends Gadget {
    public String OS;
    public boolean hasSIMCardSlot;
    public Tablet(double screenSize, int yearOfIssue, String name, boolean hasBluetooth,
                  boolean hasWiFi, String OS, boolean hasSIMCardSlot) {
        super(screenSize, yearOfIssue, name, hasBluetooth, hasWiFi);
        this.OS = OS;
        this.hasSIMCardSlot = hasSIMCardSlot;
    }
}
