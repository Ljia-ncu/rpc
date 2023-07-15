package org.fullstack.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalRegistry implements Registry {

    private Map<String, ServiceBean> map = new ConcurrentHashMap<>(128);

    @Override
    public void register(ServiceBean serviceBean) {
        map.put(serviceBean.getClassName(), serviceBean);
    }

    @Override
    public ServiceBean getServiceBean(String serviceName) {
        return map.get(serviceName);
    }
}
