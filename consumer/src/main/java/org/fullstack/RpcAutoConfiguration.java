package org.fullstack;

import org.fullstack.client.ZkClient;
import org.fullstack.client.Client;
import org.fullstack.client.NettyClient;
import org.fullstack.core.ReferenceProxy;
import org.fullstack.discovery.ServiceDiscover;
import org.fullstack.discovery.ZkServiceDiscover;
import org.fullstack.loadbalancer.LoadBalancer;
import org.fullstack.loadbalancer.RandomLoadBalancer;
import org.fullstack.protocol.JsonProtocol;
import org.fullstack.protocol.Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RpcAutoConfiguration {

    @Value("${rpc.zk.connect:127.0.0.1:2181}")
    private String zkConnectString;

    @Bean
    @ConditionalOnMissingBean(Client.class)
    public Client client() {
        return new NettyClient();
    }

    @Bean
    @ConditionalOnMissingBean(ServiceDiscover.class)
    public ServiceDiscover serviceDiscover() {
        return new ZkServiceDiscover();
    }

    @Bean
    public ReferenceProxy referenceProxy() {
        return new ReferenceProxy();
    }

    @Bean
    @ConditionalOnMissingBean(LoadBalancer.class)
    public LoadBalancer loadBalancer() {
        return new RandomLoadBalancer();
    }

    @Bean
    @ConditionalOnMissingBean(Protocol.class)
    public Protocol protocol() {
        return new JsonProtocol();
    }

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(zkConnectString, 3000, 3);
    }

}
