package org.fullstack.model;

import java.io.Serializable;

public class ExposedBean implements Serializable {

    private String serviceName;

    private String protocol;

    private String ip;

    private Integer port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public ExposedBean() {
    }

    public ExposedBean(String serviceName, String protocol, String ip, Integer port) {
        this.serviceName = serviceName;
        this.protocol = protocol;
        this.ip = ip;
        this.port = port;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "ExposedBean{" +
                "serviceName='" + serviceName + '\'' +
                ", protocol='" + protocol + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
