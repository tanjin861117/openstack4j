//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.openstack4j.openstack.identity.v3.domain;

import com.fasterxml.jackson.annotation.*;
import com.google.common.base.Objects;
import org.openstack4j.model.identity.v3.Domain;
import org.openstack4j.model.identity.v3.Project;
import org.openstack4j.model.identity.v3.builder.ProjectBuilder;
import org.openstack4j.openstack.common.ListResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonRootName("project")
@JsonIgnoreProperties(
        value = {"extra"},
        ignoreUnknown = true
)
public class KeystoneProject implements Project {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private KeystoneDomain domain;
    @JsonProperty("domain_id")
    private String domainId;
    private String description;
    @JsonIgnore
    private Map<String, String> links;
    @JsonProperty("parent_id")
    private String parentId;
    private String subtree;
    private String parents;
    private Boolean enabled = true;
    @JsonProperty
    private List tags;
    private Map<String, String> extra = new HashMap();

    public KeystoneProject() {
    }

    public static ProjectBuilder builder() {
        return new KeystoneProject.ProjectConcreteBuilder();
    }

    public ProjectBuilder toBuilder() {
        return new KeystoneProject.ProjectConcreteBuilder(this);
    }

    public List getTags() {
        return tags;
    }

    public void setTags(List tags) {
        this.tags = tags;
    }

    public String getId() {
        return this.id;
    }

    public Domain getDomain() {
        return this.domain;
    }

    public String getDomainId() {
        if (this.domainId == null && this.domain != null && this.domain.getId() != null) {
            this.domainId = this.domain.getId();
        }

        return this.domainId;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    @JsonIgnore
    public Map<String, String> getLinks() {
        return this.links;
    }

    @JsonProperty("links")
    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public String getParentId() {
        return this.parentId;
    }

    public String getSubtree() {
        return this.subtree;
    }

    public String getParents() {
        return this.parents;
    }

    public boolean isEnabled() {
        return this.enabled != null && this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getExtra(String key) {
        return (String) this.extra.get(key);
    }

    @JsonAnyGetter
    public Map<String, String> getExtra() {
        return this.extra;
    }

    @JsonAnySetter
    public void setExtra(String key, String value) {
        if (!Objects.equal(key, "is_domain")) {
            this.extra.put(key, value);
        }
    }

    public String toString() {
        String domainId = null;
        if (this.domain != null) {
            domainId = this.domain.getId();
        }

        return Objects.toStringHelper(this).add("id", this.id).add("domainId", domainId).add("description", this.description).add("name", this.name).add("links", this.links).add("parentId", this.parentId).add("subtree", this.subtree).add("parents", this.parents).add("enabled", this.enabled).omitNullValues().toString();
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.id, this.domain != null ? this.domain.getId() : this.domainId, this.description, this.name, this.links, this.parentId, this.subtree, this.parents});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            KeystoneProject that = (KeystoneProject) KeystoneProject.class.cast(obj);
            return Objects.equal(this.id, that.id) && Objects.equal(this.domain, that.domain) && Objects.equal(this.description, that.description) && Objects.equal(this.name, that.name) && Objects.equal(this.links, that.links) && Objects.equal(this.parentId, that.parentId) && Objects.equal(this.subtree, that.subtree) && Objects.equal(this.parents, that.parents) && Objects.equal(this.enabled, that.enabled);
        } else {
            return false;
        }
    }

    public static class Projects extends ListResult<KeystoneProject> {
        private static final long serialVersionUID = 1L;
        @JsonProperty("projects")
        protected List<KeystoneProject> list;

        public Projects() {
        }

        public List<KeystoneProject> value() {
            return this.list;
        }
    }

    public static class ProjectConcreteBuilder implements ProjectBuilder {
        KeystoneProject model;

        ProjectConcreteBuilder() {
            this(new KeystoneProject());
        }

        ProjectConcreteBuilder(KeystoneProject model) {
            this.model = model;
        }

        public ProjectBuilder id(String id) {
            this.model.id = id;
            return this;
        }

        public ProjectBuilder domain(Domain domain) {
            if (domain != null && domain.getId() != null) {
                this.model.domainId = domain.getId();
            }

            return this;
        }

        public ProjectBuilder description(String description) {
            this.model.description = description;
            return this;
        }

        public ProjectBuilder name(String name) {
            this.model.name = name;
            return this;
        }

        public ProjectBuilder links(Map<String, String> links) {
            this.model.links = links;
            return this;
        }

        public ProjectBuilder parentId(String parentId) {
            this.model.parentId = parentId;
            return this;
        }

        public ProjectBuilder subtree(String subtree) {
            this.model.subtree = subtree;
            return this;
        }

        public ProjectBuilder parents(String parents) {
            this.model.parents = parents;
            return this;
        }

        public ProjectBuilder setExtra(String key, String value) {
            this.model.extra.put(key, value);
            return this;
        }

        public ProjectBuilder enabled(boolean enabled) {
            this.model.enabled = enabled;
            return this;
        }

        public Project build() {
            return this.model;
        }

        public ProjectBuilder from(Project in) {
            if (in != null) {
                this.model = (KeystoneProject) in;
            }

            return this;
        }

        public ProjectBuilder domainId(String domainId) {
            this.model.domainId = domainId;
            return this;
        }
    }
}
