package com.company;

public class Phone extends Gadget {
    public String OS;
    public double cameraMegaPixels;
    public Phone(String OS, double screenSize,  double cameraMegaPixels, int yearOfIssue) {
        super(screenSize, yearOfIssue);
        this.OS = OS;
        this.cameraMegaPixels = cameraMegaPixels;
    }
}
