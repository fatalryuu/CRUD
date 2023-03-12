package com.example.crud.hierarchy;

public class Tablet extends Gadget {
    public String OS;
    public boolean hasSIMCardSlot;

    public Tablet(String name, double screenSize, int yearOfIssue, boolean hasBluetooth,
                  boolean hasWiFi, String OS, boolean hasSIMCardSlot) {
        super(name, screenSize, yearOfIssue, hasBluetooth, hasWiFi);
        this.OS = OS;
        this.hasSIMCardSlot = hasSIMCardSlot;
    }
}
