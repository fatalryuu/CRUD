package com.example.crud.plugins;

public interface Plugin {
    String getExtension();
    String getName();
    byte[] encode(byte[] data);
    byte[] decode(byte[] data);
}
