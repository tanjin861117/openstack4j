package org.openstack4j.openstack.networking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.base.Objects;
import org.openstack4j.api.Apis;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.NetworkType;
import org.openstack4j.model.network.State;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.model.network.builder.NetworkBuilder;
import org.openstack4j.openstack.common.ListResult;

import java.util.ArrayList;
import java.util.List;

@JsonRootName("network")
public class NeutronNetwork
        implements Network {
    private static final long serialVersionUID = 1L;
    private State status;

    @JsonProperty
    private List<String> subnets;
    private List<NeutronSubnet> neutronSubnets;
    private String name;

    @JsonProperty("provider:physical_network")
    private String providerPhyNet;

    @JsonProperty("admin_state_up")
    private Boolean adminStateUp;

    @JsonProperty("tenant_id")
    private String tenantId;

    @JsonProperty("provider:network_type")
    private NetworkType networkType;

    @JsonProperty("router:external")
    private Boolean routerExternal;
    private String id;
    private Boolean shared;

    @JsonProperty("provider:segmentation_id")
    private String providerSegID;

    private String mtu;

    public static NetworkBuilder builder() {
        return new NetworkConcreteBuilder();
    }

    public NetworkBuilder toBuilder() {
        return new NetworkConcreteBuilder(this);
    }

    public String getMtu() {
        return mtu;
    }

    public void setMtu(String mtu) {
        this.mtu = mtu;
    }

    public State getStatus() {
        return this.status;
    }

    public List<String> getSubnets() {
        return this.subnets;
    }

    public List<? extends Subnet> getNeutronSubnets() {
        if ((this.neutronSubnets == null) && (this.subnets != null) && (this.subnets.size() > 0)) {
            this.neutronSubnets = new ArrayList();
            for (String subnetId : this.subnets) {
                NeutronSubnet sub = (NeutronSubnet) Apis.getNetworkingServices().subnet().get(subnetId);
                this.neutronSubnets.add(sub);
            }
        }
        return this.neutronSubnets;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderPhyNet() {
        return this.providerPhyNet;
    }

    public boolean isAdminStateUp() {
        return (this.adminStateUp != null) && (this.adminStateUp.booleanValue());
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public NetworkType getNetworkType() {
        return this.networkType;
    }

    @JsonIgnore
    public boolean isRouterExternal() {
        return (this.routerExternal != null) && (this.routerExternal.booleanValue());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isShared() {
        return (this.shared != null) && (this.shared.booleanValue());
    }

    public String getProviderSegID() {
        return this.providerSegID;
    }

    public String toString() {
        return Objects.toStringHelper(this).omitNullValues()
                .add("name", this.name)
                .add("status", this.status).add("subnets", this.subnets).add("provider:physical_network", this.providerPhyNet)
                .add("adminStateUp", this.adminStateUp)
                .add("tenantId", this.tenantId).add("provider:network_type", this.networkType).add("router:external", this.routerExternal)
                .add("id", this.id)
                .add("shared", this.shared).add("provider:segmentation_id", this.providerSegID)
                .toString();
    }

    public static class NetworkConcreteBuilder
            implements NetworkBuilder {
        private NeutronNetwork m;

        public NetworkConcreteBuilder() {
            this(new NeutronNetwork());
        }

        public NetworkConcreteBuilder(NeutronNetwork m) {
            this.m = m;
        }

        public NetworkBuilder name(String name) {
            this.m.name = name;
            return this;
        }

        public NetworkBuilder adminStateUp(boolean adminStateUp) {
            this.m.adminStateUp = Boolean.valueOf(adminStateUp);
            return this;
        }

        public NetworkBuilder networkType(NetworkType networkType) {
            this.m.networkType = networkType;
            return this;
        }

        public NetworkBuilder physicalNetwork(String providerPhysicalNetwork) {
            this.m.providerPhyNet = providerPhysicalNetwork;
            return this;
        }

        public NetworkBuilder segmentId(String providerSegmentationId) {
            this.m.providerSegID = providerSegmentationId;
            return this;
        }

        public NetworkBuilder tenantId(String tenantId) {
            this.m.tenantId = tenantId;
            return this;
        }

        public NetworkBuilder isShared(boolean shared) {
            this.m.shared = Boolean.valueOf(shared);
            return this;
        }

        public NetworkBuilder isRouterExternal(boolean routerExternal) {
            this.m.routerExternal = Boolean.valueOf(routerExternal);
            return this;
        }

        public Network build() {
            return this.m;
        }

        public NetworkBuilder from(Network in) {
            this.m = ((NeutronNetwork) in);
            return this;
        }
    }

    public static class Networks extends ListResult<NeutronNetwork> {
        private static final long serialVersionUID = 1L;

        @JsonProperty("networks")
        private List<NeutronNetwork> networks;

        public List<NeutronNetwork> value() {
            return this.networks;
        }
    }
}