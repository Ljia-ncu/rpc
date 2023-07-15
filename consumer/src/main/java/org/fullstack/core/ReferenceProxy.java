package org.fullstack.core;

import org.fullstack.client.Client;
import org.fullstack.discovery.ServiceDiscover;
import org.fullstack.loadbalancer.LoadBalancer;
import org.fullstack.model.ExposedBean;
import org.fullstack.model.Request;
import org.fullstack.model.Response;
import org.fullstack.protocol.Protocol;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class ReferenceProxy<T> implements InvocationHandler {

    private Class<T> clazz;

    @Autowired
    private Client client;

    @Autowired
    private ServiceDiscover serviceDiscover;

    @Autowired
    private LoadBalancer loadBalanced;

    @Autowired
    private Protocol protocol;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setServiceDiscover(ServiceDiscover serviceDiscover) {
        this.serviceDiscover = serviceDiscover;
    }

    public void setLoadBalanced(LoadBalancer loadBalanced) {
        this.loadBalanced = loadBalanced;
    }

    public void setMessageProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public ReferenceProxy() {
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Client getClient() {
        return client;
    }

    public ServiceDiscover getServiceDiscover() {
        return serviceDiscover;
    }

    public LoadBalancer getLoadBalanced() {
        return loadBalanced;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        String serviceName = this.clazz.getName();
        List<ExposedBean> services = serviceDiscover.getServices(serviceName);
        ExposedBean service = loadBalanced.choose(services);

        Request request = new Request(serviceName, method.getName(), method.getParameterTypes(), args);
        byte[] data = protocol.serialize(request);
        byte[] responseData = client.request(service, data);
        Response response = protocol.deserialize(responseData, Response.class);
        if (response.getException() != null || response.getCode() != 200) {
            throw response.getException();
        }
        return response.getResult();
    }
}