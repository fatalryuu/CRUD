package com.example.crud;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ObjectInfo {
    private SimpleIntegerProperty id;
    private SimpleStringProperty objectName;
    private SimpleStringProperty objectType;

    public ObjectInfo(int id, String objectName, String objectType) {
        this.id = new SimpleIntegerProperty(id);
        this.objectName = new SimpleStringProperty(objectName);
        this.objectType = new SimpleStringProperty(objectType);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getObjectName() {
        return objectName.get();
    }

    public void setObjectName(String objectName) {
        this.objectName.set(objectName);
    }

    public String getObjectType() {
        return objectType.get();
    }

    public void setObjectType(String objectType) {
        this.objectType.set(objectType);
    }
}
