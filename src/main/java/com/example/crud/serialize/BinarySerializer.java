package com.example.crud.serialize;

import com.example.crud.hierarchy.Gadget;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;

import static com.example.crud.MainController.createAlert;

public class BinarySerializer implements Serializer {
    public BinarySerializer() {

    }
    @Override
    public void serialize(ArrayList<Gadget> gadgets, String path) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            outputStream.writeObject(gadgets);
        } catch (IOException e) {
            createAlert(Alert.AlertType.ERROR, "File error", "Error while binary file serialization!", "Check file info");
        }
    }

    @Override
    public ArrayList<Gadget> deserialize(String path) {
        ArrayList<Gadget> newList = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) {
            newList = (ArrayList<Gadget>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            createAlert(Alert.AlertType.ERROR, "File error", "Error while binary file deserialization!", "Check file info");
        }
        return newList;
    }
}
