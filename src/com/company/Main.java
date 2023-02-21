package com.company;

public class Main {

    public static void main(String[] args) {
        Gadget gadget = new Gadget(5.7, 2016);
        Phone phone = new Phone("IOS", 6.1, 12, 2022);
        Xiaomi xiaomiRedmi5 = new Xiaomi("Android", 5.7, 12, 2017, "redmi");
        IPhone iPhone11 = new IPhone("IOS", 6.06, 12, 2019, "default", 11);
        Person nikita = new Person("Nikita", "Krashevskiy", 18, xiaomiRedmi5);
        Person ivan = new Person("Ivan", "Shatko", 18, iPhone11);
        System.out.println("Hello, my name is "
                + nikita.firstName + " and I have a gadget with a "
                + gadget.screenSize + " screen size.\n"
                + "My phone has camera with " + phone.cameraMegaPixels
                + " mega pixels. But my friend "
                + ivan.firstName + " has a " + ivan.smartphone.yearOfIssue + " "
                + iPhone11.type + " " + iPhone11.OS + " smartphone.");
    }
}
