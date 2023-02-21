package com.company;

public class Phone extends Gadget {
    public String OS;
    public double cameraMegaPixels;
    public Phone(String OS, double screenSize,  double cameraMegaPixels, int yearOfIssue, String name) {
        super(screenSize, yearOfIssue, name);
        this.OS = OS;
        this.cameraMegaPixels = cameraMegaPixels;
    }
}
