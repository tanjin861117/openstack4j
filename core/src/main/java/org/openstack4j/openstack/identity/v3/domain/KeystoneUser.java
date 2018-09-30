package org.openstack4j.openstack.identity.v3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.base.Objects;
import org.openstack4j.model.identity.v3.Domain;
import org.openstack4j.model.identity.v3.User;
import org.openstack4j.model.identity.v3.builder.UserBuilder;
import org.openstack4j.openstack.common.ListResult;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonRootName("user")
public class KeystoneUser implements User {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private KeystoneDomain domain;
    @JsonProperty("domain_id")
    private String domainId;
    private String email;
    private String password;
    private String description;
    @JsonProperty("default_project_id")
    private String defaultProjectId;
    private Map<String, String> links;
    private Boolean enabled = Boolean.valueOf(true);

    private String created_at;

    public KeystoneUser() {
    }

    public static UserBuilder builder() {
        return new KeystoneUser.UserConcreteBuilder();
    }

    public UserBuilder toBuilder() {
        return new KeystoneUser.UserConcreteBuilder(this);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDomainId() {
        return this.domainId;
    }

    public Domain getDomain() {
        return this.domain;
    }

    public String getDefaultProjectId() {
        return this.defaultProjectId;
    }

    public Map<String, String> getLinks() {
        return this.links;
    }

    public boolean isEnabled() {
        return this.enabled != null && this.enabled.booleanValue();
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String toString() {
        return Objects.toStringHelper(this).omitNullValues().add("name", this.name).add("id", this.id).add("email", this.email).add("password", this.password).add("description", this.description).add("domainId", this.domainId).add("links", this.links).add("enabled", this.enabled).add("defaultProjectId", this.defaultProjectId).toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            KeystoneUser that = (KeystoneUser) KeystoneUser.class.cast(obj);
            return Objects.equal(this.name, that.name) && Objects.equal(this.id, that.id) && Objects.equal(this.email, that.email) && Objects.equal(this.password, that.password) && Objects.equal(this.description, that.description) && Objects.equal(this.domainId, that.domainId) && Objects.equal(this.links, that.domainId) && Objects.equal(this.enabled, that.enabled) && Objects.equal(this.defaultProjectId, that.defaultProjectId);
        } else {
            return false;
        }
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public static class UserConcreteBuilder implements UserBuilder {
        KeystoneUser model;

        UserConcreteBuilder() {
            this(new KeystoneUser());
        }

        UserConcreteBuilder(KeystoneUser model) {
            this.model = model;
        }

        public UserBuilder id(String id) {
            this.model.id = id;
            return this;
        }

        public User build() {
            return this.model;
        }

        public UserBuilder from(User in) {
            if (in != null) {
                this.model = (KeystoneUser) in;
            }

            return this;
        }

        public UserBuilder name(String name) {
            this.model.name = name;
            return this;
        }

        public UserBuilder defaultProjectId(String defaultProjectId) {
            this.model.defaultProjectId = defaultProjectId;
            return this;
        }

        public UserBuilder domainId(String domainId) {
            this.model.domainId = domainId;
            return this;
        }

        public UserBuilder domain(Domain domain) {
            if (domain != null && domain.getId() != null) {
                this.model.domainId = domain.getId();
            }

            return this;
        }

        public UserBuilder email(String email) {
            this.model.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.model.password = password;
            return this;
        }

        public UserBuilder links(Map<String, String> links) {
            this.model.links = links;
            return this;
        }

        public UserBuilder enabled(boolean enabled) {
            this.model.enabled = Boolean.valueOf(enabled);
            return this;
        }

        public UserBuilder description(String description) {
            this.model.description = description;
            return this;
        }
    }

    public static class Users extends ListResult<KeystoneUser> {
        private static final long serialVersionUID = 1L;
        @JsonProperty("users")
        private List<KeystoneUser> list;

        public Users() {
        }

        public List<KeystoneUser> value() {
            return this.list;
        }
    }
}
