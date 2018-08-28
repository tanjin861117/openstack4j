package org.openstack4j.api.networking;

import org.openstack4j.common.RestService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.NetworkUpdate;

import java.util.List;
import java.util.Map;

public abstract interface NetworkService extends RestService {
    public abstract List<? extends Network> listAll(Map<String, String> paramMap);

    public abstract List<? extends Network> list(Map<String, String> paramMap);

    public abstract List<? extends Network> list();

    public abstract Network get(String paramString);

    public abstract Network update(String paramString, NetworkUpdate paramNetworkUpdate);

    public abstract ActionResponse delete(String paramString);

    public abstract Network create(Network paramNetwork);
}