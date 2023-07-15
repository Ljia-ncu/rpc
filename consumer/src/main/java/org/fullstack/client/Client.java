package org.fullstack.client;

import org.fullstack.model.ExposedBean;

public interface Client {
    byte[] request(ExposedBean service, byte[] data);
}
