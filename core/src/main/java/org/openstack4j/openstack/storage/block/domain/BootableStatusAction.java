package org.openstack4j.openstack.storage.block.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.openstack4j.model.ModelEntity;

@JsonRootName("os-set_bootable")
public class BootableStatusAction implements ModelEntity {

    private static final long serialVersionUID = 1L;

    @JsonProperty("bootable")
    private final Boolean bootable;

    public BootableStatusAction(Boolean bootable) {
        this.bootable = bootable;
    }

    public static BootableStatusAction create(Boolean bootable) {
        return new BootableStatusAction(bootable);
    }

    public Boolean getBootable() {
        return bootable;
    }
}