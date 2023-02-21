package com.company;

public class IPhone extends Phone {
    public String type; //default/pro/pro max/mini
    public int series;
    public IPhone(String name, String OS, double screenSize, double cameraMegaPixels, int yearOfIssue, String type, int series) {
        super(OS, screenSize, cameraMegaPixels, yearOfIssue, name);
        this.type = type;
        this.series = series;
    }
}
