package org.openstack4j.api.compute.ext;

import org.openstack4j.common.RestService;
import org.openstack4j.model.compute.ext.Hypervisor;
import org.openstack4j.model.compute.ext.HypervisorStatistics;
import org.openstack4j.openstack.compute.domain.ext.ExtHypervisorServer;

import java.util.List;

/**
 * API which supports the "os-hypervisors" extension.  For more details
 *
 * @author Jeremy Unruh
 * @See http://developer.openstack.org/api-ref-compute-v2-ext.html#ext-os-hypervisors
 */
public interface HypervisorService extends RestService {

    /**
     * The Hypervisors for this OpenStack deployment.
     * <p>
     * NOTE: This is an extension and not all deployments support os-hypervisors
     *
     * @return the available hypervisors in detail
     */
    List<? extends Hypervisor> list();

    List<ExtHypervisorServer> listServers(String hypervisor_hostname);

    /**
     * The Hypervisor Statistics for this OpenStack Deployment
     * <p>
     * NOTE: This is an extension and not all deployments support os-hypervisors
     *
     * @return the hypervisor statistics
     */
    HypervisorStatistics statistics();

}
