package com.example.crud.serialize;

import com.example.crud.hierarchy.*;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Alert;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.crud.MainController.createAlert;

public class JSONSerializer implements Serializer {

    private final RuntimeTypeAdapterFactory<Gadget> typeAdapterFactory = RuntimeTypeAdapterFactory.of(Gadget.class, "type")
            .registerSubtype(Tablet.class, "tablet").registerSubtype(Laptop.class, "laptop")
            .registerSubtype(Smartphone.class, "smartphone").registerSubtype(PushButtonPhone.class, "pushButtonPhone");

    public JSONSerializer() {

    }

    @Override
    public void serialize(ArrayList<Gadget> gadgets, String path) {
        Gson gson = new Gson().newBuilder().registerTypeAdapterFactory(typeAdapterFactory).create();
        Type type = new TypeToken<ArrayList<Gadget>>(){}.getType();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write(gson.toJson(gadgets, type));
        } catch (IOException e) {
            createAlert(Alert.AlertType.ERROR, "File error", "Error while JSON file serialization!", "Check file info");
        }
    }

    @Override
    public ArrayList<Gadget> deserialize(String path) {
        Gson gson = new Gson().newBuilder().registerTypeAdapterFactory(typeAdapterFactory).create();
        Type type = new TypeToken<ArrayList<Gadget>>(){}.getType();
        String json = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            json = bufferedReader.readLine();
        } catch (IOException e) {
            createAlert(Alert.AlertType.ERROR, "File error", "Error while JSON file deserialization!", "Check file info");
        }
        return gson.fromJson(json, type);
    }
}