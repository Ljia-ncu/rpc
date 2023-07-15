package org.fullstack.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;

public class ReferenceFactoryBean<T> implements FactoryBean<T>, ApplicationContextAware {

    private Class<T> type;

    private ApplicationContext applicationContext;

    public ReferenceFactoryBean(Class<T> type) {
        this.type = type;
    }

    @Override
    public T getObject() {
        ReferenceProxy referenceProxy = applicationContext.getBean(ReferenceProxy.class);
        referenceProxy.setClazz(type);
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, referenceProxy);
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}