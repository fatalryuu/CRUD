package com.example.crud.hierarchy;

import com.example.crud.Name;
import com.example.crud.Type;

import java.io.Serializable;

public class Camera implements Serializable {
    public double megapixels;
    public double zoom;
    public Camera(double megapixels, double zoom) {
        this.megapixels = megapixels;
        this.zoom = zoom;
    }
    public Camera() {

    }

    @Name("Camera megapixels")
    @Type("Double")
    public double getMegapixels() {
        return megapixels;
    }

    @Name("Camera megapixels")
    public void setMegapixels(double megapixels) {
        this.megapixels = megapixels;
    }

    @Name("Camera zoom")
    @Type("Double")
    public double getZoom() {
        return zoom;
    }

    @Name("Camera zoom")
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
