package org.openstack4j.api.compute.ext;

import org.openstack4j.api.compute.vo.HypervisorVO;
import org.openstack4j.model.compute.ext.Service;

import java.util.List;

/**
 * API which supports the "os-services" extension.
 *
 * @author Stephan Latour
 */
public interface ServicesService {

    /**
     * List services info
     * <p>
     * NOTE: This is an extension and not all deployments support os-services
     *
     * @return a list of nova services
     */
    List<? extends Service> list();

    Service disableService(HypervisorVO service);

    Service enableService(HypervisorVO service);
}