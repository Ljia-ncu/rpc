package org.fullstack.protocol;

import org.fullstack.util.JsonUtil;

public class JsonProtocol implements Protocol {

    @Override
    public byte[] serialize(Object obj) {
        return JsonUtil.toJsonByte(obj);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JsonUtil.parse(data, clazz);
    }
}
