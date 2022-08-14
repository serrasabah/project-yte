package yte.app.application.Job.controller.response;

import yte.app.application.Job.Entity.Job;

public record JobQueryModel(
         Long id,
         String jobName,
         String URL,
         Long period,
         Long timeout,
         Long healthy,
         Long unhealthy
) {
    public JobQueryModel(Job job) {
        this(
                job.getId(),
                job.getJobName(),
                job.getURL(),
                job.getPeriod(),
                job.getTimeout(),
                job.getHealthy(),
                job.getUnhealthy()
        );
    }
}
