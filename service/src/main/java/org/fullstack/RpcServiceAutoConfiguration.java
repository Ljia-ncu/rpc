package org.fullstack;

import io.netty.channel.ChannelHandler;
import org.fullstack.client.ZkClient;
import org.fullstack.core.NettyServer;
import org.fullstack.core.RpcBeanPostProcessor;
import org.fullstack.core.RpcHandler;
import org.fullstack.core.ServerHandler;
import org.fullstack.expose.Expose;
import org.fullstack.expose.ZkExpose;
import org.fullstack.protocol.JsonProtocol;
import org.fullstack.protocol.Protocol;
import org.fullstack.registry.LocalRegistry;
import org.fullstack.registry.Registry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(RpcBeanPostProcessor.class)
public class RpcServiceAutoConfiguration {

    @Value("${rpc.zk.connect:127.0.0.1:2181}")
    private String zkConnectString;

    @Bean
    public NettyServer nettyServer() {
        return new NettyServer();
    }

    @Bean
    public ServerHandler serverHandler() {
        return new ServerHandler();
    }

    @Bean
    public RpcHandler rpcHandler() {
        return new RpcHandler();
    }

    @Bean
    @ConditionalOnMissingBean(Protocol.class)
    public Protocol protocol() {
        return new JsonProtocol();
    }

    @Bean
    public Registry registry() {
        return new LocalRegistry();
    }

    @Bean
    public Expose expose() {
        return new ZkExpose();
    }

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(zkConnectString, 3000, 3);
    }
}
