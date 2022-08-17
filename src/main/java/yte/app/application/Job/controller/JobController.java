package yte.app.application.Job.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yte.app.application.Job.pojo.JobStatusCount;
import yte.app.application.Job.controller.request.AddJobRequest;
import yte.app.application.Job.controller.request.UpdateJobRequest;
import yte.app.application.Job.controller.response.JobQueryModel;
import yte.app.application.Job.service.JobService;
import yte.app.application.common.response.MessageResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@Validated
public class JobController {

    private final JobService jobService;

    @PostMapping
    public MessageResponse addJob(@Valid @RequestBody AddJobRequest addJobRequest) {
        return jobService.addJob(addJobRequest.toDomainEntity());
    }


    @GetMapping("/{id}")
    public Map<String, JobStatusCount> getByIdResult(@NotNull @PathVariable Long id) {
        return jobService.getByIdResult(id);
    }

    @GetMapping("/name")
    public List<JobQueryModel> getAllJobByUser() {
        return jobService.getAllJobByUser()
                .stream()
                .map(job -> new JobQueryModel(job))
                .toList();
    }

    @GetMapping
    public List<JobQueryModel> getAllJobs() {
        return jobService.getAllJob()
                .stream()
                .map(job -> new JobQueryModel(job))
                .toList();
    }

    @PutMapping("/{id}")
    public MessageResponse updateJob(@Valid @RequestBody UpdateJobRequest request, @PathVariable Long id) {
        return jobService.updateJob(id, request.toDomainEntity());
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteJob(@PathVariable @NotNull Long id ){
        return jobService.deleteJob(id);
    }
}
