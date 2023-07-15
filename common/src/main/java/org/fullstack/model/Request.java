package org.fullstack.model;

import java.io.Serializable;
import java.util.Arrays;

public class Request implements Serializable {

    private String className;

    private String methodName;

    private Class<?>[] paramsType;

    private Object[] args;

    public Request() {
    }

    public Request(String className, String methodName, Class<?>[] paramsType, Object[] args) {
        this.className = className;
        this.methodName = methodName;
        this.paramsType = paramsType;
        this.args = args;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamsType() {
        return paramsType;
    }

    public void setParamsType(Class<?>[] paramsType) {
        this.paramsType = paramsType;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "Request{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramsType=" + Arrays.toString(paramsType) +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
