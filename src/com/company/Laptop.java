package com.company;

public class Laptop extends Gadget {
    public int memory;
    public String CPU;
    public boolean hasTouchpad;
    public String OS;
    public Laptop(double screenSize, int yearOfIssue, String name,
                  boolean hasBluetooth, boolean hasWiFi, int memory,
                  String CPU, boolean hasTouchpad, String OS) {
        super(screenSize, yearOfIssue, name, hasBluetooth, hasWiFi);
        this.memory = memory;
        this.CPU = CPU;
        this.hasTouchpad = hasTouchpad;
        this.OS = OS;
    }
}

