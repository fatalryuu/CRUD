package com.company;

public class Main {

    public static void main(String[] args) {
        Gadget gadget = new Gadget(6.06, 2019, "IPhone");
        Laptop macbook = new Laptop(13.3, 2020, "Air", 16, "Apple M1");
        Phone phone = new Phone("IOS", 6.1, 12, 2022, "IPhone");
        Xiaomi xiaomiRedmi5 = new Xiaomi("Xiaomi", "Android", 5.7, 12, 2017, "Redmi");
        IPhone iPhone11 = new IPhone("IPhone", "IOS", 6.06, 12, 2019, "default", 11);
        Person nikita = new Person("Nikita", "Krashevskiy", 18, xiaomiRedmi5);
        Person ivan = new Person("Ivan", "Shatko", 18, iPhone11);
        System.out.println("Hello, my name is "
                + ivan.firstName + " and I have a gadget named "
                + gadget.name + " with a " + ivan.smartphone.screenSize
                + " screen size.\n" + "My phone has camera with " + ivan.smartphone.cameraMegaPixels
                + " mega pixels. My friend " + nikita.firstName + " has a " + nikita.smartphone.yearOfIssue
                + " " + nikita.smartphone.name + " " + xiaomiRedmi5.model + " smartphone.");
    }
}
