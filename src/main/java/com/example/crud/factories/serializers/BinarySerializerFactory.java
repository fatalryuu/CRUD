package com.example.crud.factories.serializers;

import com.example.crud.serialize.impl.BinarySerializer;
import com.example.crud.serialize.Serializer;

public class BinarySerializerFactory implements SerializerFactory {
    @Override
    public Serializer getSerializer() {
        return new BinarySerializer();
    }
}
