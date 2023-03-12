package com.example.crud.hierarchy;

import javafx.stage.Stage;

public class Laptop extends Gadget {
    public int memory;
    public boolean hasTouchpad;
    public String OS;
    public Laptop(String name, double screenSize, int yearOfIssue,
                  boolean hasBluetooth, boolean hasWiFi, String OS,
                  int memory, boolean hasTouchpad) {
        super(name, screenSize, yearOfIssue, hasBluetooth, hasWiFi);
        this.memory = memory;
        this.hasTouchpad = hasTouchpad;
        this.OS = OS;
    }
}

