//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.openstack4j.openstack.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;
import org.openstack4j.model.ModelEntity;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.identity.AuthStore;
import org.openstack4j.model.identity.AuthVersion;
import org.openstack4j.model.identity.v3.Authentication.Identity;
import org.openstack4j.openstack.common.Auth.Type;
import org.openstack4j.openstack.common.BasicResourceEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 重写KeystoneAuth类，实现管理员登录scope=system
 */
@JsonRootName("auth")
public class KeystoneAuthSys implements ModelEntity, AuthStore {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private KeystoneAuthSys.AuthIdentity identity;
    private String scope;
    @JsonIgnore
    private transient Type type;

    public KeystoneAuthSys() {
    }

    public KeystoneAuthSys(String tokenId, String scope) {
        this.identity = KeystoneAuthSys.AuthIdentity.createTokenType(tokenId);
        this.scope = scope;
        this.type = Type.TOKEN;
    }

    public KeystoneAuthSys(String user, String password, Identifier domain, String scope) {
        this.identity = KeystoneAuthSys.AuthIdentity.createCredentialType(user, password, domain);
        this.scope = scope;
        this.type = Type.CREDENTIALS;
    }

    protected KeystoneAuthSys(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public Identity getIdentity() {
        return this.identity;
    }

    public String getScope() {
        return this.scope;
    }

    @JsonIgnore
    public String getUsername() {
        return this.identity.getPassword().getUser().getName();
    }

    @JsonIgnore
    public String getPassword() {
        return this.identity.getPassword().getUser().getPassword();
    }

    @JsonIgnore
    public String getId() {
        return this.identity.getPassword().getUser().getDomain().getId();
    }

    @JsonIgnore
    public String getName() {
        return this.identity.getPassword().getUser().getDomain().getName();
    }

    @JsonIgnore
    public AuthVersion getVersion() {
        return AuthVersion.V3;
    }

    public static final class AuthIdentity implements Identity, Serializable {
        private static final long serialVersionUID = 1L;
        private KeystoneAuthSys.AuthIdentity.AuthPassword password;
        private KeystoneAuthSys.AuthIdentity.AuthToken token;
        private List<String> methods = Lists.newArrayList();

        public AuthIdentity() {
        }

        static KeystoneAuthSys.AuthIdentity createTokenType(String tokenId) {
            KeystoneAuthSys.AuthIdentity identity = new KeystoneAuthSys.AuthIdentity();
            identity.methods.add("token");
            identity.token = new KeystoneAuthSys.AuthIdentity.AuthToken(tokenId);
            return identity;
        }

        static KeystoneAuthSys.AuthIdentity createCredentialType(String username, String password) {
            return createCredentialType(username, password, (Identifier) null);
        }

        static KeystoneAuthSys.AuthIdentity createCredentialType(String username, String password, Identifier domain) {
            KeystoneAuthSys.AuthIdentity identity = new KeystoneAuthSys.AuthIdentity();
            identity.password = new KeystoneAuthSys.AuthIdentity.AuthPassword(username, password, domain);
            identity.methods.add("password");
            return identity;
        }

        public Password getPassword() {
            return this.password;
        }

        public Token getToken() {
            return this.token;
        }

        public List<String> getMethods() {
            return this.methods;
        }

        public static final class AuthPassword implements Password, Serializable {
            private static final long serialVersionUID = 1L;
            private KeystoneAuthSys.AuthIdentity.AuthPassword.AuthUser user;

            public AuthPassword() {
            }

            public AuthPassword(String username, String password, Identifier domain) {
                this.user = new KeystoneAuthSys.AuthIdentity.AuthPassword.AuthUser(username, password, domain);
            }

            public User getUser() {
                return this.user;
            }

            public static final class AuthUser extends BasicResourceEntity implements User {
                private static final long serialVersionUID = 1L;
                private KeystoneAuthSys.AuthIdentity.AuthPassword.AuthUser.AuthDomain domain;
                private String password;

                public AuthUser() {
                }

                public AuthUser(String username, String password, Identifier domainIdentifier) {
                    this.password = password;
                    if (domainIdentifier != null) {
                        this.domain = new KeystoneAuthSys.AuthIdentity.AuthPassword.AuthUser.AuthDomain();
                        if (domainIdentifier.isTypeID()) {
                            this.domain.setId(domainIdentifier.getId());
                        } else {
                            this.domain.setName(domainIdentifier.getId());
                        }

                        this.setName(username);
                    } else {
                        this.setId(username);
                    }

                }

                public org.openstack4j.model.identity.v3.Authentication.Identity.Password.User.Domain getDomain() {
                    return this.domain;
                }

                public String getPassword() {
                    return this.password;
                }

                public static final class AuthDomain extends BasicResourceEntity implements org.openstack4j.model.identity.v3.Authentication.Identity.Password.User.Domain {
                    private static final long serialVersionUID = 1L;

                    public AuthDomain() {
                    }
                }
            }
        }

        public static final class AuthToken implements Token, Serializable {
            private static final long serialVersionUID = 1L;
            @JsonProperty
            private String id;

            AuthToken() {
            }

            AuthToken(String id) {
                this.id = id;
            }

            public String getId() {
                return this.id;
            }
        }
    }
}
