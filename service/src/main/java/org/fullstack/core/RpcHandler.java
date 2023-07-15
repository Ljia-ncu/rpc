package org.fullstack.core;

import org.fullstack.model.Request;
import org.fullstack.model.Response;
import org.fullstack.protocol.Protocol;
import org.fullstack.registry.Registry;
import org.fullstack.registry.ServiceBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

public class RpcHandler {

    @Autowired
    private Protocol protocol;

    @Autowired
    private Registry registry;

    public byte[] handle(byte[] data) {
        Response response;
        try {
            Request request = protocol.deserialize(data, Request.class);
            ServiceBean serviceBean = registry.getServiceBean(request.getClassName());
            Method method = serviceBean.getClazz().getMethod(request.getMethodName(), request.getParamsType());
            Object result = method.invoke(serviceBean.getBean(), request.getArgs());
            response = new Response(result, 200);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(new RuntimeException(e.getMessage()), 500);
        }
        return protocol.serialize(response);
    }
}
