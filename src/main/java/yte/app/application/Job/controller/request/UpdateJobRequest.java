package yte.app.application.Job.controller.request;

import yte.app.application.Job.Entity.Job;

import javax.validation.constraints.Min;

public record UpdateJobRequest(
        String jobName,
        String URL,
        @Min(1)
        Long period,
        Long timeout
) {
    public Job toDomainEntity() {
        return new Job(jobName, null, period, timeout);
    }
}
