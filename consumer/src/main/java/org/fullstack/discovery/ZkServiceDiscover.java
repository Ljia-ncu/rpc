package org.fullstack.discovery;

import org.fullstack.client.ZkClient;
import org.fullstack.model.ExposedBean;
import org.fullstack.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ZkServiceDiscover implements ServiceDiscover {

    @Autowired
    private ZkClient zkClient;

    @Override
    public List<ExposedBean> getServices(String serviceName) {
        List<String> services = zkClient.discover(serviceName);
        List<ExposedBean> list = new ArrayList<>();
        for (String service : services) {
            list.add(JsonUtil.parse(service, ExposedBean.class));
        }
        return list;
        //   return services.stream().map(service -> JsonUtil.parse(service, ExposedBean.class)).collect(Collectors.toList());
    }
}
