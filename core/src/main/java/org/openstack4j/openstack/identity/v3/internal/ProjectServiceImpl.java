package org.openstack4j.openstack.identity.v3.internal;

import org.openstack4j.api.identity.v3.ProjectService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.identity.v3.Project;
import org.openstack4j.openstack.identity.v3.domain.KeystoneProject;
import org.openstack4j.openstack.identity.v3.domain.KeystoneProject.Projects;
import org.openstack4j.openstack.internal.BaseOpenStackService;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.openstack4j.core.transport.ClientConstants.PATH_PROJECTS;

public class ProjectServiceImpl extends BaseOpenStackService implements ProjectService {

    @Override
    public Project create(Project project) {
        checkNotNull(project);
        return post(KeystoneProject.class, PATH_PROJECTS).entity(project).execute();
    }

    @Override
    public Project create(String domainId, String name, String description, boolean enabled) {
        checkNotNull(domainId);
        checkNotNull(name);
        checkNotNull(description);
        checkNotNull(enabled);
        return create(KeystoneProject.builder().domainId(domainId).name(name).description(description).enabled(enabled).build());
    }

    @Override
    public Project get(String projectId) {
        checkNotNull(projectId);
        return get(KeystoneProject.class, PATH_PROJECTS, "/", projectId).execute();
    }

    @Override
    public List<? extends Project> getByName(String projectName) {
        checkNotNull(projectName);
        return get(Projects.class, uri(PATH_PROJECTS)).param("name", projectName).execute().getList();
    }

    @Override
    public Project getByName(String projectName, String domainId) {
        checkNotNull(projectName);
        checkNotNull(domainId);
        return get(Projects.class, uri(PATH_PROJECTS)).param("name", projectName).param("domain_id", domainId).execute().first();
    }

    @Override
    public Project update(Project project) {
        checkNotNull(project);
        return patch(KeystoneProject.class, PATH_PROJECTS, "/", project.getId()).entity(project).execute();
    }

    @Override
    public ActionResponse delete(String projectId) {
        checkNotNull(projectId);
        return deleteWithResponse(PATH_PROJECTS, "/", projectId).execute();
    }

    @Override
    public List<? extends Project> list() {
        return get(Projects.class, uri(PATH_PROJECTS)).execute().getList();
    }

    public List<? extends Project> list(Map<String, String> filterMap) {
        Invocation<Projects> imageInvocation = buildInvocation(filterMap);
        return imageInvocation.execute().getList();
    }

    private Invocation<Projects> buildInvocation(Map<String, String> filteringParams) {
        Invocation<Projects> imageInvocation = get(Projects.class, uri(PATH_PROJECTS));
        if (filteringParams == null) {
            return imageInvocation;
        }
        if (filteringParams != null) {
            for (Map.Entry<String, String> entry : filteringParams.entrySet()) {
                imageInvocation = imageInvocation.param(entry.getKey(), entry.getValue());
            }
        }
        return imageInvocation;
    }
}
