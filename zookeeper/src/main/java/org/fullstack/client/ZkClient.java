package org.fullstack.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;

import java.util.List;
import java.util.function.Consumer;

public class ZkClient implements Registry {

    private final CuratorFramework client;

    public static final String PREFIX = "/";

    public ZkClient(String connectString, int sleepTimeMs, int retries) {
        client = CuratorFrameworkFactory.newClient(connectString, new ExponentialBackoffRetry(sleepTimeMs, retries));
        client.start();
    }

    @Override
    public void register(String service, String metadata) {
        try {
            String root = PREFIX + service;
            String child = root + PREFIX + metadata;
            if (client.checkExists().forPath(root) == null) {
                client.create().withMode(CreateMode.PERSISTENT).forPath(root);
            }
            if (client.checkExists().forPath(child) == null) {
                client.create().withMode(CreateMode.EPHEMERAL).forPath(child);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> discover(String service, Consumer<List<String>> watcher) {
        try {
            String root = PREFIX + service;
            client.getChildren().usingWatcher(new CuratorWatcher() {
                @Override
                public void process(WatchedEvent watchedEvent) throws Exception {
                    String path = watchedEvent.getPath();
                    client.getChildren().usingWatcher(this).forPath(path);
                    List<String> newServices = client.getChildren().forPath(path);
                    watcher.accept(newServices);
                }
            }).forPath(root);
            return client.getChildren().forPath(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> discover(String service) {
        try {
            String root = PREFIX + service;
            return client.getChildren().forPath(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
