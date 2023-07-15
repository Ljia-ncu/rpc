package org.fullstack.client;

import io.netty.channel.ChannelId;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class RpcFuture {

    private ChannelId id;

    private byte[] data;

    private CountDownLatch countDownLatch;

    private final static ConcurrentHashMap<ChannelId, RpcFuture> resultMap = new ConcurrentHashMap<>(128);

    public RpcFuture(ChannelId id) {
        this.id = id;
        resultMap.put(id, this);
        countDownLatch = new CountDownLatch(1);
    }

    public byte[] getResult() {
        try {
            this.countDownLatch.await();
            resultMap.remove(this.id);
            return this.data;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public static void setResult(ChannelId id, byte[] data) {
        if (data != null) {
            RpcFuture rpcFuture = resultMap.get(id);
            rpcFuture.setData(data);
            rpcFuture.getCountDownLatch().countDown();
        }
    }
}
