package org.fullstack.service;

import org.fullstack.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RpcService {

    public List<String> getInfo(Integer size) {
        return IntStream.range(0, size).mapToObj(String::valueOf)
                .collect(Collectors.toList());
    }

    public String echo(String hello) {
        return hello;
    }

    public void test() {
        System.out.println("test");
    }
}
