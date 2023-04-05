package com.example.crud.Utils;

import com.example.crud.hierarchy.Camera;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

public class Maps {
    public static HashMap<String, Method> getMapOfSettersOrGetters(String type, Class thisClass) {
        HashMap<String, Method> map = new HashMap<>();
        for (Method method : Arrays.stream(thisClass.getMethods()).filter(x -> x.getName().startsWith(type)).toList()) {
            if (method.isAnnotationPresent(Name.class)) {
                map.put(method.getAnnotation(Name.class).value(), method);
            }
        }
        return map;
    }

    public static HashMap<String, String> getMapOfTypes(Class thisClass) {
        HashMap<String, String> mapOfTypes = new HashMap<>();
        for (Method method : Arrays.stream(thisClass.getMethods()).filter(x -> x.getName().startsWith("get")).toList()) {
            if (method.isAnnotationPresent(Type.class) && method.isAnnotationPresent(Name.class)) {
                if (!method.getAnnotation(Name.class).value().equals("Camera"))
                    mapOfTypes.put(method.getAnnotation(Name.class).value(), method.getAnnotation(Type.class).value());
                else {
                    Class<?> camera = Camera.class;
                    for (Method cameraMethod : Arrays.stream(camera.getMethods()).filter(x -> x.getName().startsWith("get")).toList()) {
                        if (cameraMethod.isAnnotationPresent(Name.class) && cameraMethod.isAnnotationPresent(Name.class)) {
                            mapOfTypes.put(cameraMethod.getAnnotation(Name.class).value(), cameraMethod.getAnnotation(Type.class).value());
                        }
                    }
                }
            }
        }
        return mapOfTypes;
    }
}
