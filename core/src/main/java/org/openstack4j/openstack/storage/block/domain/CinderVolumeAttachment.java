//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.openstack4j.openstack.storage.block.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.base.Objects;
import org.openstack4j.model.storage.block.VolumeAttachment;

@JsonRootName("volumeAttachment")
public class CinderVolumeAttachment implements VolumeAttachment {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String device;
    @JsonProperty
    private String host_name;
    @JsonProperty
    private String id;
    @JsonProperty
    private String server_id;
    @JsonProperty
    private String volume_id;

    public CinderVolumeAttachment() {
    }

    public String getDevice() {
        return this.device;
    }

    public String getHostname() {
        return this.host_name;
    }

    public void setHostname(String hostName) {
        this.host_name = hostName;
    }

    public String getId() {
        return this.id;
    }

    public String getServerId() {
        return this.server_id;
    }

    public String getVolumeId() {
        return this.volume_id;
    }

    public String toString() {
        return Objects.toStringHelper(this).omitNullValues().add("device", this.device).add("id", this.id).add("serverId", this.server_id).add("volumeId", this.volume_id).add("hostname", this.host_name).toString();
    }
}
