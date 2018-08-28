package org.openstack4j.openstack.networking.internal;

import com.google.common.base.Preconditions;
import org.openstack4j.api.networking.RouterService;
import org.openstack4j.core.transport.ExecutionOptions;
import org.openstack4j.core.transport.propagation.PropagateOnStatus;
import org.openstack4j.model.ModelEntity;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.network.AttachInterfaceType;
import org.openstack4j.model.network.HostRoute;
import org.openstack4j.model.network.Router;
import org.openstack4j.model.network.RouterInterface;
import org.openstack4j.model.network.builder.RouterBuilder;
import org.openstack4j.openstack.internal.BaseOpenStackService;
import org.openstack4j.openstack.networking.domain.AddRouterInterfaceAction;
import org.openstack4j.openstack.networking.domain.NeutronRouter;
import org.openstack4j.openstack.networking.domain.NeutronRouterInterface;

import java.util.List;
import java.util.Map;

public class RouterServiceImpl extends BaseNetworkingServices
        implements RouterService {
    private BaseOpenStackService.Invocation<NeutronRouter.Routers> buildInvocation(Map<String, String> filteringParams) {
        BaseOpenStackService.Invocation invocation = get(NeutronRouter.Routers.class, new String[]{"/routers"});
        if (filteringParams == null) {
            return invocation;
        }
        for (Map.Entry entry : filteringParams.entrySet()) {
            invocation = invocation.param((String) entry.getKey(), entry.getValue());
        }

        return invocation;
    }

    public List<? extends Router> listAll(Map<String, String> filteringParams) {
        BaseOpenStackService.Invocation invocation = buildInvocation(filteringParams);

        int limit = 25;
        if ((filteringParams != null) && (filteringParams.containsKey("limit"))) {
            limit = Integer.parseInt((String) filteringParams.get("limit"));
        }

        List totalList = ((NeutronRouter.Routers) invocation.execute()).getList();
        List currList = totalList;
        while (currList.size() == limit) {
            invocation.updateParam("marker", ((NeutronRouter) currList.get(limit - 1)).getId());
            currList = ((NeutronRouter.Routers) invocation.execute()).getList();
            totalList.addAll(currList);
        }

        return totalList;
    }

    public List<? extends Router> list(Map<String, String> filteringParams) {
        BaseOpenStackService.Invocation invocation = buildInvocation(filteringParams);
        return ((NeutronRouter.Routers) invocation.execute()).getList();
    }

    public List<? extends Router> list() {
        return ((NeutronRouter.Routers) get(NeutronRouter.Routers.class, new String[]{uri("/routers", new Object[0])}).execute()).getList();
    }

    public Router get(String routerId) {
        Preconditions.checkNotNull(routerId);
        return (Router) get(NeutronRouter.class, new String[]{uri("/routers/%s", new Object[]{routerId})}).execute();
    }

    public ActionResponse delete(String routerId) {
        Preconditions.checkNotNull(routerId);
        return (ActionResponse) deleteWithResponse(new String[]{uri("/routers/%s", new Object[]{routerId})}).execute();
    }

    public Router create(String name, boolean adminStateUp) {
        Preconditions.checkNotNull(name);
        return (Router) post(NeutronRouter.class, new String[]{uri("/routers", new Object[0])}).entity((ModelEntity) NeutronRouter.builder().name(name).adminStateUp(adminStateUp).build()).execute();
    }

    public Router create(Router router) {
        Preconditions.checkNotNull(router);
        return (Router) post(NeutronRouter.class, new String[]{uri("/routers", new Object[0])}).entity(router).execute();
    }

    public Router update(Router router) {
        Preconditions.checkNotNull(router);
        Preconditions.checkNotNull(router.getId());

        RouterBuilder rb = NeutronRouter.builder().name(router.getName()).adminStateUp(router.isAdminStateUp()).externalGateway(router.getExternalGatewayInfo());
        List<HostRoute> routes = (List<HostRoute>) router.getRoutes();

        if ((routes != null) && (!routes.isEmpty())) {
            for (HostRoute route : routes)
                rb.route(route.getDestination(), route.getNexthop());
        } else {
            rb.noRoutes();
        }

        return (Router) put(NeutronRouter.class, new String[]{uri("/routers/%s", new Object[]{router.getId()})})
                .entity((ModelEntity) rb
                        .build())
                .execute();
    }

    public Router toggleAdminStateUp(String routerId, boolean adminStateUp) {
        Preconditions.checkNotNull(routerId);
        return (Router) put(NeutronRouter.class, new String[]{uri("/routers/%s", new Object[]{routerId})}).entity((ModelEntity) NeutronRouter.builder().adminStateUp(adminStateUp).build()).execute();
    }

    public RouterInterface attachInterface(String routerId, AttachInterfaceType type, String portOrSubnetId) {
        Preconditions.checkNotNull(routerId);
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(portOrSubnetId);

        return (RouterInterface) put(NeutronRouterInterface.class, new String[]{uri("/routers/%s/add_router_interface", new Object[]{routerId})})
                .entity(AddRouterInterfaceAction.create(type, portOrSubnetId))
                .execute();
    }

    public RouterInterface detachInterface(String routerId, String subnetId, String portId) {
        Preconditions.checkNotNull(routerId);
        Preconditions.checkState((subnetId != null) || (portId != null), "Either a Subnet or Port identifier must be set");

        return (RouterInterface) put(NeutronRouterInterface.class, new String[]{uri("/routers/%s/remove_router_interface", new Object[]{routerId})})
                .entity(new NeutronRouterInterface(subnetId, portId))
                .execute((ExecutionOptions)ExecutionOptions.create(PropagateOnStatus.on(404)));
    }
}