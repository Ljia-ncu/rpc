package org.fullstack.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.fullstack.model.ExposedBean;
import org.springframework.beans.factory.InitializingBean;

public class NettyClient implements Client, InitializingBean {
    private Bootstrap bootstrap;
    @Override
    public byte[] request(ExposedBean service, byte[] data) {
        try {
            ChannelFuture channelFuture = bootstrap.connect(service.getIp(), service.getPort()).sync();
            ByteBuf byteBuf = Unpooled.buffer(data.length);
            byteBuf.writeBytes(data);
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(byteBuf);
            return new RpcFuture(channel.id()).getResult();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new NettyClientHandler());
                    }
                });
    }
}
