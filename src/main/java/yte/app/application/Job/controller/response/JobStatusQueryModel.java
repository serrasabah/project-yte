package yte.app.application.Job.controller.response;

import yte.app.application.Job.Entity.Job;
import yte.app.application.Job.Entity.JobStatus;

public record JobStatusQueryModel(
        Long id,
        boolean Reachable
) {
    public JobStatusQueryModel(JobStatus jobStatus) {
        this(
                jobStatus.getId(),
                jobStatus.isReachable()

        );
    }
}
