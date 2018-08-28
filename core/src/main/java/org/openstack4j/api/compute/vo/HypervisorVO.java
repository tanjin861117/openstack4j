package org.openstack4j.api.compute.vo;

import org.openstack4j.model.ModelEntity;

/**
 * Created by chendi0921 on 2017/7/4.
 */
public class HypervisorVO implements ModelEntity {

    private static final long serialVersionUID = 1L;

    private String host;
    private String binary;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }
}
