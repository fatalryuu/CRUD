package com.example.crud.hierarchy;

import com.example.crud.Utils.Name;
import com.example.crud.Utils.Type;

import java.io.Serializable;

@Name("Gadget")
public abstract class Gadget implements Serializable {
    public String name;
    public double screenSize;
    public int yearOfIssue;
    public boolean hasBluetooth;
    public boolean hasWiFi;
    public Gadget(String name, double screenSize, int yearOfIssue, boolean hasBluetooth, boolean hasWiFi) {
        this.screenSize = screenSize;
        this.yearOfIssue = yearOfIssue;
        this.name = name;
        this.hasBluetooth = hasBluetooth;
        this.hasWiFi = hasWiFi;
    }
    public Gadget() {

    }

    @Name("Name")
    public void setName(String name) {
        this.name = name;
    }

    @Name("Screen size")
    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    @Name("Year of issue")
    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    @Name("Has Bluetooth")
    public void setHasBluetooth(boolean hasBluetooth) {
        this.hasBluetooth = hasBluetooth;
    }

    @Name("Has WiFi")
    public void setHasWiFi(boolean hasWiFi) {
        this.hasWiFi = hasWiFi;
    }

    @Name("Name")
    @Type("String")
    public String getName() {
        return name;
    }

    @Name("Screen size")
    @Type("Double")
    public double getScreenSize() {
        return screenSize;
    }

    @Name("Year of issue")
    @Type("Integer")
    public int getYearOfIssue() {
        return yearOfIssue;
    }

    @Name("Has Bluetooth")
    @Type("Boolean")
    public boolean getHasBluetooth() {
        return hasBluetooth;
    }

    @Name("Has WiFi")
    @Type("Boolean")
    public boolean getHasWiFi() {
        return hasWiFi;
    }
}
