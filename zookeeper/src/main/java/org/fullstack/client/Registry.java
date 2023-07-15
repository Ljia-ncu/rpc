package org.fullstack.client;

import java.util.List;
import java.util.function.Consumer;

public interface Registry {

    void register(String service, String metadata);

    List<String> discover(String service, Consumer<List<String>> watcher);

    List<String> discover(String service);
}
