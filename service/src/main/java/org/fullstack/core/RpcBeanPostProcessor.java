package org.fullstack.core;

import org.fullstack.annotation.Service;
import org.fullstack.expose.Expose;
import org.fullstack.model.ExposedBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.fullstack.registry.Registry;
import org.fullstack.registry.ServiceBean;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RpcBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private Registry registry;

    @Autowired
    private Expose expose;

    @Value("${rpc.server.port:8848}")
    private int port;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if (clazz.isAnnotationPresent(Service.class)) {
            Service rpcServer = clazz.getAnnotation(Service.class);
//            if (!StringUtils.hasText(rpcServer.name())) {
//                throw new RuntimeException("service name must not be null");
//            }
            ServiceBean serviceBean = new ServiceBean(clazz.getName(), clazz, bean);
            registry.register(serviceBean);
            try {
                String ip = InetAddress.getLocalHost().getHostAddress();
                ExposedBean exposedBean = new ExposedBean(clazz.getName(), rpcServer.protocol(), ip, port);
                expose.expose(exposedBean);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return bean;
    }
}
