package yte.app.application.Job.controller.request;

import yte.app.application.Job.Entity.Job;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record AddJobRequest(


        @NotBlank (message = " field cannot be empty")
        @Size(max = 25)
         String jobName,
        @NotBlank
         String URL,


         Long period,


         Long timeout
) {
    public Job toDomainEntity() {

        return new Job(jobName, URL, period, timeout);
    }
}
