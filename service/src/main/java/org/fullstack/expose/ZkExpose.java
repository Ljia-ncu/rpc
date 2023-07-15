package org.fullstack.expose;

import org.fullstack.client.ZkClient;
import org.fullstack.model.ExposedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.fullstack.util.JsonUtil;

public class ZkExpose implements Expose {

    @Autowired
    private ZkClient client;

    @Override
    public void expose(ExposedBean exposedBean) {
        String metaData = JsonUtil.toJsonStr(exposedBean);
        client.register(exposedBean.getServiceName(), metaData);
    }
}
