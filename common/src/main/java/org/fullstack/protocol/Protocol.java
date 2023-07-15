package org.fullstack.protocol;

public interface Protocol {

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] data, Class<T> clazz);
}
