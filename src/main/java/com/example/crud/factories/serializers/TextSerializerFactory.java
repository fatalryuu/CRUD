package com.example.crud.factories.serializers;

import com.example.crud.serialize.Serializer;
import com.example.crud.serialize.TextSerializer;

public class TextSerializerFactory implements SerializerFactory {
    @Override
    public Serializer getSerializer() {
        return new TextSerializer();
    }
}
