package org.openstack4j.openstack.compute.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.base.Objects;
import org.openstack4j.model.compute.VolumeAttachment;
import org.openstack4j.openstack.common.ListResult;

import java.util.List;

/**
 * return a description for this volume attachment job
 *
 * @author Octopus Zhang
 */
@JsonRootName("volumeAttachment")
@JsonIgnoreProperties(ignoreUnknown = true)
public class NovaVolume implements VolumeAttachment {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private String device;

    @JsonProperty
    private String id;

    @JsonProperty
    private String serverId;

    @JsonProperty
    private String volumeId;

    private String volumName;

    public NovaVolume() {
    }

    private NovaVolume(String volumeId, String device) {
        this.volumeId = volumeId;
        this.device = device;
    }

    public static NovaVolume create(String volumeId, String device) {
        return new NovaVolume(volumeId, device);
    }

    @Override
    public String getDevice() {
        return device;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getServerId() {
        return serverId;
    }

    @Override
    public String getVolumeId() {
        return volumeId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).omitNullValues()
                .add("device", device).add("id", id).add("serverId", serverId)
                .add("volumeId", volumeId).toString();

    }

    public String getVolumName() {
        return volumName;
    }

    public void setVolumName(String volumName) {
        this.volumName = volumName;
    }


    public static class NovaVolumes extends ListResult<NovaVolume> {

        private static final long serialVersionUID = 1L;

        @JsonProperty("volumeAttachments")
        private List<NovaVolume> volumeAttachments;

        @Override
        public List<NovaVolume> value() {
            return volumeAttachments;
        }
    }

}
