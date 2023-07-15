package org.fullstack.loadbalancer;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer implements LoadBalancer {
    @Override
    public <T> T choose(List<T> services) {
        int size = services.size();
        return services.get(new Random().nextInt(size));
    }
}
