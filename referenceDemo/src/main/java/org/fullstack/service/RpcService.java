package org.fullstack.service;

import org.fullstack.annotation.Reference;

import java.util.List;

@Reference
public interface RpcService {

    List<String> getInfo(Integer size);

    String echo(String hello);

    void test();

}
