package org.openstack4j.model.heat;

import org.openstack4j.model.ModelEntity;

public interface StackOutput extends ModelEntity {
    String getOutputKey();

    String getDescription();
}
