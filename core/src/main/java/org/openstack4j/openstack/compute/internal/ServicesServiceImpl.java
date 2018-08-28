package org.openstack4j.openstack.compute.internal;

import org.openstack4j.api.compute.ext.ServicesService;
import org.openstack4j.api.compute.vo.HypervisorVO;
import org.openstack4j.model.compute.ext.Service;
import org.openstack4j.openstack.compute.domain.ext.ExtService;
import org.openstack4j.openstack.compute.domain.ext.ExtService.Services;

import java.util.List;

/**
 * Compute Services service provides CRUD capabilities for nova service(s).
 *
 * @author Stephan Latour
 */
public class ServicesServiceImpl extends BaseComputeServices implements ServicesService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Service> list() {
        return get(Services.class, uri("/os-services")).execute().getList();
    }

    public Service disableService(HypervisorVO service) {
        return put(ExtService.class, uri("/os-services/disable")).entity(service).execute();
    }

    public Service enableService(HypervisorVO service) {
        return put(ExtService.class, uri("/os-services/enable")).entity(service).execute();
    }

}