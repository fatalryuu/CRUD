package com.example.crud.hierarchy;

import com.example.crud.Utils.Name;
import com.example.crud.Utils.Type;

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
    public Laptop() {

    }

    @Name("Memory(GB)")
    @Type("Integer")
    public int getMemory() {
        return memory;
    }

    @Name("Memory(GB)")
    public void setMemory(int memory) {
        this.memory = memory;
    }

    @Name("Has touchpad")
    @Type("Boolean")
    public boolean getHasTouchpad() {
        return hasTouchpad;
    }

    @Name("Has touchpad")
    public void setHasTouchpad(boolean hasTouchpad) {
        this.hasTouchpad = hasTouchpad;
    }

    @Name("OS")
    @Type("String")
    public String getOS() {
        return OS;
    }

    @Name("OS")
    public void setOS(String OS) {
        this.OS = OS;
    }
}

