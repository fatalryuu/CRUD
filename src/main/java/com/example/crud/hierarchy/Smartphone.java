package com.example.crud.hierarchy;

import com.example.crud.Name;
import com.example.crud.Type;
import javafx.stage.Stage;

public class Smartphone extends Phone {
    public String supportedNetwork; //2G, 3G
    public String OS;
    public Smartphone(String name, double screenSize, int yearOfIssue, boolean hasBluetooth,
                      boolean hasWiFi, Camera camera, String model, int amountOfSIMCards,
                      String OS, String supportedNetwork) {
        super(name, screenSize, yearOfIssue,  hasBluetooth, hasWiFi, camera, model, amountOfSIMCards);
        this.supportedNetwork = supportedNetwork;
        this.OS = OS;
    }
    public Smartphone() {

    }

    @Name("Supported network")
    @Type("String")
    public String getSupportedNetwork() {
        return supportedNetwork;
    }

    @Name("Supported network")
    public void setSupportedNetwork(String supportedNetwork) {
        this.supportedNetwork = supportedNetwork;
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
