package org.fullstack.registry;

public class ServiceBean {

    private String className;

    private Class<?> clazz;

    private Object bean;

    public ServiceBean(String className, Class<?> clazz, Object bean) {
        this.className = className;
        this.clazz = clazz;
        this.bean = bean;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
