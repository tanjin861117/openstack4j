//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.openstack4j.core.transport.internal;

import org.openstack4j.api.exceptions.ConnectorNotFoundException;
import org.openstack4j.core.transport.HttpExecutorService;
import org.openstack4j.core.transport.HttpRequest;
import org.openstack4j.core.transport.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

public class HttpExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(HttpExecutor.class);
    private static final HttpExecutor INSTANCE = new HttpExecutor();
    private HttpExecutorService service;

    private HttpExecutor() {
    }

    public static HttpExecutor create() {
        return INSTANCE;
    }

    private HttpExecutorService service() {
        if (this.service != null) {
            return this.service;
        } else {
            Iterator<HttpExecutorService> it = ServiceLoader.load(HttpExecutorService.class, this.getClass().getClassLoader()).iterator();
            if (!it.hasNext()) {
                LOG.error("No OpenStack4j connector found in classpath");
                throw new ConnectorNotFoundException("No OpenStack4j connector found in classpath");
            } else {
                return this.service = (HttpExecutorService) it.next();
            }
        }
    }

    public String getExecutorName() {
        return this.service().getExecutorDisplayName();
    }

    public <R> HttpResponse execute(HttpRequest<R> request) {
        LOG.debug("Executing Request: {} -> {}", request.getEndpoint(), request.getPath());
        LOG.debug("Executing Request getQueryParams: {}", request.getQueryParams());
        LOG.debug("Executing Request getHeaders: {}", request.getHeaders());
        LOG.debug("Executing Request getEntity: {}", (request.getEntity()));
        LOG.debug("execute method:{}" , request.getMethod().name());
        return this.service().execute(request);
    }
}
