package org.fullstack.discovery;

import org.fullstack.model.ExposedBean;

import java.util.List;

public interface ServiceDiscover {

    List<ExposedBean> getServices(String serviceName);
}
