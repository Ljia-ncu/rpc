package org.fullstack.loadbalancer;

import java.util.List;

public interface LoadBalancer {

    <T> T choose(List<T> services);

}
