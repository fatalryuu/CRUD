package com.example.crud.serialize;

import com.example.crud.hierarchy.Gadget;

import java.util.ArrayList;

public interface Serializer {
    String getName();
    String getExtension();
    void serialize(ArrayList<Gadget> gadgets, String path);
    ArrayList<Gadget> deserialize(String path);
}
