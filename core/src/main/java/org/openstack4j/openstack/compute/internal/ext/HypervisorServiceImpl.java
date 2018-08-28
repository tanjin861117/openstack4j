package org.openstack4j.openstack.compute.internal.ext;

import org.openstack4j.api.compute.ext.HypervisorService;
import org.openstack4j.model.compute.ext.Hypervisor;
import org.openstack4j.model.compute.ext.HypervisorStatistics;
import org.openstack4j.openstack.compute.domain.ext.ExtHypervisor.Hypervisors;
import org.openstack4j.openstack.compute.domain.ext.ExtHypervisorServer;
import org.openstack4j.openstack.compute.domain.ext.ExtHypervisorServer.HypervisorServers;
import org.openstack4j.openstack.compute.domain.ext.ExtHypervisorStatistics;
import org.openstack4j.openstack.compute.internal.BaseComputeServices;

import java.util.List;

/**
 * Implementation for the OSHypervisors.
 */
public class HypervisorServiceImpl extends BaseComputeServices implements HypervisorService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Hypervisor> list() {
		return get(Hypervisors.class, "/os-hypervisors/detail").execute().getList();
	}

	public List<ExtHypervisorServer> listServers(String hypervisor_hostname) {
		return get(HypervisorServers.class, uri("/os-hypervisors/%s/servers", hypervisor_hostname)).execute().getList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HypervisorStatistics statistics() {
		return get(ExtHypervisorStatistics.class, "/os-hypervisors/statistics").execute();
	}

}
