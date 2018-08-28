package org.openstack4j.api.networking;

import org.openstack4j.common.RestService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.network.AttachInterfaceType;
import org.openstack4j.model.network.Router;
import org.openstack4j.model.network.RouterInterface;

import java.util.List;
import java.util.Map;

public abstract interface RouterService extends RestService {
    public abstract List<? extends Router> listAll(Map<String, String> paramMap);

    public abstract List<? extends Router> list(Map<String, String> paramMap);

    public abstract List<? extends Router> list();

    public abstract Router get(String paramString);

    public abstract ActionResponse delete(String paramString);

    public abstract Router create(String paramString, boolean paramBoolean);

    public abstract Router create(Router paramRouter);

    public abstract Router update(Router paramRouter);

    public abstract Router toggleAdminStateUp(String paramString, boolean paramBoolean);

    public abstract RouterInterface attachInterface(String paramString1, AttachInterfaceType paramAttachInterfaceType, String paramString2);

    public abstract RouterInterface detachInterface(String paramString1, String paramString2, String paramString3);
}