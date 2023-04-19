package com.example.crud.Tests;

import com.example.crud.Utils.Maps;
import com.example.crud.hierarchy.*;
import com.example.crud.serialize.impl.TextSerializer;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TextSerializerTest {
    private void initFile(ArrayList<Gadget> gadgets) {
        TextSerializer textSerializer = new TextSerializer();
        textSerializer.serialize(gadgets, "text.txt");
    }

    private void compareTwoArraysOfGadgets(ArrayList<Gadget> gadgets1, ArrayList<Gadget> gadgets2) throws InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < gadgets1.size(); i++) {
            HashMap<String, Method> map = Maps.getMapOfSettersOrGetters("get", gadgets1.get(i).getClass());
            for (String key : map.keySet()) {
                if (map.get(key).invoke(gadgets1.get(i)).getClass().getSimpleName().equals("Camera")) {
                    Camera camera = (Camera) map.get(key).invoke(gadgets1.get(i));
                    Camera camera1 = (Camera) map.get(key).invoke(gadgets2.get(i));
                    HashMap<String, Method> map1 = Maps.getMapOfSettersOrGetters("get", Camera.class);
                    for (String key1 : map1.keySet()) {
                        assertEquals(map1.get(key1).invoke(camera), map1.get(key1).invoke(camera1));
                    }
                } else {
                    assertEquals(map.get(key).invoke(gadgets1.get(i)), map.get(key).invoke(gadgets2.get(i)));
                }
            }
        }
    }

    @Test
    public void testTextSerializeForTablet() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Gadget> gadgets = new ArrayList<>();
        gadgets.add(new Tablet("samsung a23", 4, 2014, true, true, "Android", true));
        initFile(gadgets);
        TextSerializer textSerializer = new TextSerializer();
        ArrayList<Gadget> newGadgets = textSerializer.deserialize("text.txt");
        compareTwoArraysOfGadgets(gadgets, newGadgets);
    }

    @Test
    public void testTextSerializeForLaptop() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Gadget> gadgets = new ArrayList<>();
        gadgets.add(new Laptop("ASUS", 16.3, 2004, true, true, "Linux", 64, true));
        initFile(gadgets);
        TextSerializer textSerializer = new TextSerializer();
        ArrayList<Gadget> newGadgets = textSerializer.deserialize("text.txt");
        compareTwoArraysOfGadgets(gadgets, newGadgets);
    }

    @Test
    public void testTextSerializeForSmartphone() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Gadget> gadgets = new ArrayList<>();
        gadgets.add(new Smartphone("iPhone 11", 6.8, 2019, true, true, new Camera(4.5, 2.7), "11", 1, "IOS", "4G"));
        initFile(gadgets);
        TextSerializer textSerializer = new TextSerializer();
        ArrayList<Gadget> newGadgets = textSerializer.deserialize("text.txt");
        compareTwoArraysOfGadgets(gadgets, newGadgets);
    }

    @Test
    public void testTextSerializeForPushButtonPhone() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Gadget> gadgets = new ArrayList<>();
        gadgets.add(new PushButtonPhone("TeXet TM-B226", 4, 1999, true, true, new Camera(4.5, 2.7), "swap", 1, 36));
        initFile(gadgets);
        TextSerializer textSerializer = new TextSerializer();
        ArrayList<Gadget> newGadgets = textSerializer.deserialize("text.txt");
        compareTwoArraysOfGadgets(gadgets, newGadgets);
    }

    @Test
    public void testTextSerializeForAll() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Gadget> gadgets = new ArrayList<>();
        gadgets.add(new Laptop("ASUS", 16.3, 2004, true, true, "Windows", 64, true));
        gadgets.add(new Tablet("iPad 2019", 4, 2019, true, true, "IOS", true));
        gadgets.add(new Smartphone("iPhone 11", 6.8, 2019, true, true, new Camera(12.0, 2.7), "11", 1, "IOS", "4G"));
        gadgets.add(new PushButtonPhone("BabushkaPhone", 4, 1999, true, true, new Camera(3.5, 2.7), "swap", 1, 36));
        initFile(gadgets);
        TextSerializer textSerializer = new TextSerializer();
        ArrayList<Gadget> newGadgets = textSerializer.deserialize("text.txt");
        compareTwoArraysOfGadgets(gadgets, newGadgets);
    }
}
