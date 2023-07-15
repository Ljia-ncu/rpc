package org.fullstack.model;

import java.io.Serializable;

public class Response implements Serializable {

    private Object result;

    private Exception exception;

    private Integer code;

    public Response() {
    }

    public Response(Object result, Integer code) {
        this.result = result;
        this.code = code;
    }

    public Response(Exception exception, Integer code) {
        this.exception = exception;
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Response{" +
                "result=" + result +
                ", exception=" + exception +
                ", code=" + code +
                '}';
    }
}
