package org.openstack4j.openstack.compute.domain.ext;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.openstack4j.openstack.common.ListResult;

import java.io.Serializable;
import java.util.List;

public class ExtHypervisorServer implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("hypervisor_hostname")
    private String hypervisorHostname;

    private String id;

    @JsonProperty("state")
    private String state;

    @JsonProperty("status")
    private String status;

    @JsonProperty("servers")
    private List<Servers> servers;

    public String getHypervisorHostname() {
        return hypervisorHostname;
    }

    public void setHypervisorHostname(String hypervisorHostname) {
        this.hypervisorHostname = hypervisorHostname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Servers> getServers() {
        return servers;
    }

    public void setServers(List<Servers> servers) {
        this.servers = servers;
    }

    @JsonRootName("servers")
    static class Servers implements Serializable {

        /**
         * @Fields: serialVersionUID
         * @Todo: TODO
         */
        private static final long serialVersionUID = 1L;
        private String uuid;
        private String name;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class HypervisorServers extends ListResult<ExtHypervisorServer> {

        private static final long serialVersionUID = 1L;

        @JsonProperty("hypervisors")
        List<ExtHypervisorServer> hypervisors;

        @Override
        protected List<ExtHypervisorServer> value() {
            return hypervisors;
        }
    }
}
