package com.frost_fox.jenkins.job_addon.addon.description;

import com.frost_fox.jenkins.job_addon.addon.execution.AddonExecution;

import java.util.List;
import java.util.Objects;

public class JobDescription {

    private List<BuildDescription> buildDescriptions;

    public JobDescription(List<BuildDescription> buildDescriptions) {
        this.buildDescriptions = buildDescriptions;
    }

    @SuppressWarnings("WeakerAccess")
    public List<BuildDescription> getBuildDescriptions() {
        return buildDescriptions;
    }

    @Override
    public boolean equals(Object description) {
        return description instanceof JobDescription && description.hashCode() == hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildDescriptions);
    }

    public AddonExecution getAddonByBuildIdAndJobId(String buildId, String jobId) throws Exception {
        return getBuild(buildId).getAddonById(jobId);
    }

    private BuildDescription getBuild(String buildId) throws Exception {
        return this.getBuildDescriptions().stream().filter(build -> build.getId().equals(buildId)).findFirst()
                .orElseThrow(Exception::new);
    }
}