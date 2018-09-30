package org.openstack4j.openstack.networking.internal;

import com.google.common.base.Preconditions;
import org.openstack4j.api.networking.NetworkService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.NetworkUpdate;
import org.openstack4j.openstack.internal.BaseOpenStackService;
import org.openstack4j.openstack.networking.domain.NeutronNetwork;

import java.util.List;
import java.util.Map;

public class NetworkServiceImpl extends BaseNetworkingServices
        implements NetworkService {
    private BaseOpenStackService.Invocation<NeutronNetwork.Networks> buildInvocation(Map<String, String> filteringParams) {
        BaseOpenStackService.Invocation invocation = get(NeutronNetwork.Networks.class, new String[]{"/networks"});
        if (filteringParams == null) {
            return invocation;
        }
        for (Map.Entry entry : filteringParams.entrySet()) {
            invocation = invocation.param((String) entry.getKey(), entry.getValue());
        }

        return invocation;
    }

    public List<? extends Network> listAll(Map<String, String> filteringParams) {
        BaseOpenStackService.Invocation invocation = buildInvocation(filteringParams);

        int limit = 25;
        if ((filteringParams != null) && (filteringParams.containsKey("limit"))) {
            limit = Integer.parseInt((String) filteringParams.get("limit"));
        }

        List totalList = ((NeutronNetwork.Networks) invocation.execute()).getList();
        List currList = totalList;
        while (currList.size() == limit) {
            invocation.updateParam("marker", ((NeutronNetwork) currList.get(limit - 1)).getId());
            currList = ((NeutronNetwork.Networks) invocation.execute()).getList();
            totalList.addAll(currList);
        }

        return totalList;
    }

    public List<? extends Network> list(Map<String, String> filteringParams) {
        BaseOpenStackService.Invocation invocation = buildInvocation(filteringParams);
        return ((NeutronNetwork.Networks) invocation.execute()).getList();
    }

    public List<? extends Network> list() {
        return ((NeutronNetwork.Networks) get(NeutronNetwork.Networks.class, new String[]{uri("/networks", new Object[0])}).execute()).getList();
    }

    public ActionResponse delete(String networkId) {
        Preconditions.checkNotNull(networkId);
        return (ActionResponse) deleteWithResponse(new String[]{uri("/networks/%s", new Object[]{networkId})}).execute();
    }

    public Network get(String networkId) {
        Preconditions.checkNotNull(networkId);
        return (Network) get(NeutronNetwork.class, new String[]{uri("/networks/%s", new Object[]{networkId})}).execute();
    }

    public Network create(Network network) {
        Preconditions.checkNotNull(network);
        return (Network) post(NeutronNetwork.class, new String[]{uri("/networks", new Object[0])}).entity(network).execute();
    }

    public Network update(String networkId, NetworkUpdate network) {
        Preconditions.checkNotNull(networkId);
        Preconditions.checkNotNull(network, "network");
        return (Network) put(NeutronNetwork.class, new String[]{uri("/networks/%s", new Object[]{networkId})}).entity(network).execute();
    }
}