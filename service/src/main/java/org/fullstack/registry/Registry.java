package org.fullstack.registry;

public interface Registry {
    void register(ServiceBean serviceBean);

    ServiceBean getServiceBean(String serviceName);
}
