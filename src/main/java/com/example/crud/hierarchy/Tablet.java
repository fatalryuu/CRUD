package com.example.crud.hierarchy;

import com.example.crud.Name;
import com.example.crud.Type;

@Name("Tablet")
public class Tablet extends Gadget {
    public String OS;
    public boolean hasSIMCardSlot;

    public Tablet(String name, double screenSize, int yearOfIssue, boolean hasBluetooth,
                  boolean hasWiFi, String OS, boolean hasSIMCardSlot) {
        super(name, screenSize, yearOfIssue, hasBluetooth, hasWiFi);
        this.OS = OS;
        this.hasSIMCardSlot = hasSIMCardSlot;
    }
    public Tablet() {

    }

    @Name("OS")
    @Type("String")
    public String getOS() {
        return OS;
    }

    @Name("Has SIM slot")
    @Type("Boolean")
    public boolean getHasSIMCardSlot() {
        return hasSIMCardSlot;
    }

    @Name("OS")
    public void setOS(String OS) {
        this.OS = OS;
    }

    @Name("Has SIM slot")
    public void setHasSIMCardSlot(boolean hasSIMCardSlot) {
        this.hasSIMCardSlot = hasSIMCardSlot;
    }
}
