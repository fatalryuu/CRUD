package com.company;

public class Laptop extends Gadget {
    public int memory;
    public String CPU;
    public Laptop(double screenSize, int yearOfIssue, String name, int memory, String CPU) {
        super(screenSize, yearOfIssue, name);
        this.memory = memory;
        this.CPU = CPU;
    }
}

