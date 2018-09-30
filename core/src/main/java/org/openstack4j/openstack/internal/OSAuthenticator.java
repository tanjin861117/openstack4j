//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.openstack4j.openstack.internal;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.api.client.CloudProvider;
import org.openstack4j.api.types.Facing;
import org.openstack4j.core.transport.*;
import org.openstack4j.core.transport.internal.HttpExecutor;
import org.openstack4j.model.identity.AuthStore;
import org.openstack4j.model.identity.AuthVersion;
import org.openstack4j.model.identity.v3.Token;
import org.openstack4j.openstack.common.Auth.Type;
import org.openstack4j.openstack.identity.v2.domain.*;
import org.openstack4j.openstack.identity.v3.domain.KeystoneAuth;
import org.openstack4j.openstack.identity.v3.domain.KeystoneToken;
import org.openstack4j.openstack.internal.OSClientSession.OSClientSessionV2;
import org.openstack4j.openstack.internal.OSClientSession.OSClientSessionV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSAuthenticator {
    private static final String TOKEN_INDICATOR = "Tokens";
    private static final Logger LOG = LoggerFactory.getLogger(OSAuthenticator.class);

    public OSAuthenticator() {
    }

    public static OSClient invoke(AuthStore auth, String endpoint, Facing perspective, Config config, CloudProvider provider) {
        OSAuthenticator.SessionInfo info = new OSAuthenticator.SessionInfo(endpoint, perspective, false, provider);
        return (OSClient) (auth.getVersion() == AuthVersion.V2 ? authenticateV2((Auth) auth, info, config) : authenticateV3((KeystoneAuth) auth, info, config));
    }

    public static OSClient invoke(KeystoneAuth auth, String endpoint, Facing perspective, Config config, CloudProvider provider) {
        OSAuthenticator.SessionInfo info = new OSAuthenticator.SessionInfo(endpoint, perspective, false, provider);
        return authenticateV3(auth, info, config);
    }

    public static OSClient invoke(KeystoneAuthSys auth, String endpoint, Facing perspective, Config config, CloudProvider provider) {
        OSAuthenticator.SessionInfo info = new OSAuthenticator.SessionInfo(endpoint, perspective, false, provider);
        return authenticateV3(auth, info, config);
    }

    public static OSClient invoke(TokenAuth auth, String endpoint, Facing perspective, Config config, CloudProvider provider) {
        OSAuthenticator.SessionInfo info = new OSAuthenticator.SessionInfo(endpoint, perspective, false, provider);
        return authenticateV2(auth, info, config);
    }

    public static void reAuthenticate() {
        LOG.debug("Re-Authenticating session due to expired Token or invalid response");
        OSClientSession session = OSClientSession.getCurrent();
        OSAuthenticator.SessionInfo info;
        switch (session.getAuthVersion()) {
            case V2:
                KeystoneAccess access = (KeystoneAccess) ((OSClientSessionV2) session).getAccess().unwrap();
                info = new OSAuthenticator.SessionInfo(access.getEndpoint(), session.getPerspective(), true, session.getProvider());
                org.openstack4j.openstack.common.Auth auth = (org.openstack4j.openstack.common.Auth) ((org.openstack4j.openstack.common.Auth) (access.isCredentialType() ? access.getCredentials() : access.getTokenAuth()));
                authenticateV2((Auth) auth, info, session.getConfig());
                break;
            case V3:
            default:
                Token token = ((OSClientSessionV3) session).getToken();
                info = new OSAuthenticator.SessionInfo(token.getEndpoint(), session.getPerspective(), true, session.getProvider());
                authenticateV3((KeystoneAuth) token.getCredentials(), info, session.getConfig());
        }

    }

    private static OSClientV2 authenticateV2(Auth auth, OSAuthenticator.SessionInfo info, Config config) {
        HttpRequest<KeystoneAccess> request = HttpRequest.builder(KeystoneAccess.class).header("OS4J-Auth-Command", "Tokens").endpoint(info.endpoint).method(HttpMethod.POST).path("/tokens").config(config).entity(auth).build();
        HttpResponse response = HttpExecutor.create().execute(request);
        if (response.getStatus() >= 400) {
            try {
                throw HttpExceptionHandler.mapException(response.getStatusMessage(), response.getStatus());
            } finally {
                HttpEntityHandler.closeQuietly(response);
            }
        } else {
            KeystoneAccess access = (KeystoneAccess) response.getEntity(KeystoneAccess.class);
            if (auth.getType() == Type.CREDENTIALS) {
                access = access.applyContext(info.endpoint, (Credentials) auth);
            } else if (auth.getType() == Type.RAX_APIKEY) {
                access = access.applyContext(info.endpoint, (RaxApiKeyCredentials) auth);
            } else {
                access = access.applyContext(info.endpoint, (TokenAuth) auth);
            }

            if (!info.reLinkToExistingSession) {
                return OSClientSessionV2.createSession(access, info.perspective, info.provider, config);
            } else {
                OSClientSessionV2 current = (OSClientSessionV2) OSClientSession.getCurrent();
                current.access = access;
                return current;
            }
        }
    }

    private static OSClientV3 authenticateV3(KeystoneAuth auth, OSAuthenticator.SessionInfo info, Config config) {
        HttpRequest<KeystoneToken> request = HttpRequest.builder(KeystoneToken.class).header("OS4J-Auth-Command", "Tokens").endpoint(info.endpoint).method(HttpMethod.POST).path("/auth/tokens").config(config).entity(auth).build();
        HttpResponse response = HttpExecutor.create().execute(request);
        if (response.getStatus() >= 400) {
            try {
                throw HttpExceptionHandler.mapException(response.getStatusMessage(), response.getStatus());
            } finally {
                HttpEntityHandler.closeQuietly(response);
            }
        } else {
            KeystoneToken token = (KeystoneToken) response.getEntity(KeystoneToken.class);
            token.setId(response.header("X-Subject-Token"));
            if (auth.getType() == Type.CREDENTIALS) {
                token = token.applyContext(info.endpoint, auth);
            } else if (token.getProject() != null) {
                token = token.applyContext(info.endpoint, new org.openstack4j.openstack.identity.v3.domain.TokenAuth(token.getId(), auth.getScope().getProject().getName(), auth.getScope().getProject().getId()));
            } else {
                token = token.applyContext(info.endpoint, new org.openstack4j.openstack.identity.v3.domain.TokenAuth(token.getId(), auth.getScope().getDomain().getName(), auth.getScope().getDomain().getId()));
            }

            String reqId = response.header("x-openstack-request-id");
            OSClientSessionV3 current;
            if (!info.reLinkToExistingSession) {
                current = OSClientSessionV3.createSession(token, info.perspective, info.provider, config);
                current.reqId = reqId;
                return current;
            } else {
                current = (OSClientSessionV3) OSClientSessionV3.getCurrent();
                current.token = token;
                current.reqId = reqId;
                return current;
            }
        }
    }

    private static OSClientV3 authenticateV3(KeystoneAuthSys auth, OSAuthenticator.SessionInfo info, Config config) {
        HttpRequest<KeystoneToken> request = HttpRequest.builder(KeystoneToken.class).header("OS4J-Auth-Command", "Tokens").endpoint(info.endpoint).method(HttpMethod.POST).path("/auth/tokens").config(config).entity(auth).build();
        HttpResponse response = HttpExecutor.create().execute(request);
        if (response.getStatus() >= 400) {
            try {
                throw HttpExceptionHandler.mapException(response.getStatusMessage(), response.getStatus());
            } finally {
                HttpEntityHandler.closeQuietly(response);
            }
        } else {
            KeystoneToken token = (KeystoneToken) response.getEntity(KeystoneToken.class);
            token.setId(response.header("X-Subject-Token"));
            KeystoneAuth auth2 = new KeystoneAuth();
            token = token.applyContext(info.endpoint, auth2);

            String reqId = response.header("x-openstack-request-id");
            OSClientSessionV3 current;
            if (!info.reLinkToExistingSession) {
                current = OSClientSessionV3.createSession(token, info.perspective, info.provider, config);
                current.reqId = reqId;
                return current;
            } else {
                current = (OSClientSessionV3) OSClientSessionV3.getCurrent();
                current.token = token;
                current.reqId = reqId;
                return current;
            }
        }
    }

    private static class SessionInfo {
        String endpoint;
        Facing perspective;
        boolean reLinkToExistingSession;
        CloudProvider provider;

        SessionInfo(String endpoint, Facing perspective, boolean reLinkToExistingSession, CloudProvider provider) {
            this.endpoint = endpoint;
            this.perspective = perspective;
            this.reLinkToExistingSession = reLinkToExistingSession;
            this.provider = provider;
        }
    }
}
