package com.example.crud.factories.serializers;

import com.example.crud.serialize.impl.JSONSerializer;
import com.example.crud.serialize.Serializer;

public class JSONSerializerFactory implements SerializerFactory {
    @Override
    public Serializer getSerializer() {
        return new JSONSerializer();
    }
}
