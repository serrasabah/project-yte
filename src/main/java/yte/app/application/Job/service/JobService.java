package yte.app.application.Job.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yte.app.application.Job.Entity.Job;
import yte.app.application.Job.pojo.JobStatusCount;
import yte.app.application.Job.repository.JobStatusRepository;
import yte.app.application.authentication.entity.User;
import yte.app.application.authentication.repository.UserRepository;
import yte.app.application.common.response.MessageResponse;
import yte.app.application.common.response.ResponseType;
import yte.app.application.Job.repository.JobRepository;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository ;
    private final JobStatusRepository jobStatusRepository;


    public MessageResponse addJob(Job job){
        String name = SecurityContextHolder.getContext().getAuthentication().getName() ;
        User users = userRepository.findByUsername(name).orElseThrow();

        if(jobRepository.existsByJobName(job.getJobName())) {
            return new MessageResponse(ResponseType.WARNING, "JobName already exists");
        }
        users.addJob(job);
        userRepository.save(users);

        return new MessageResponse(ResponseType.SUCCESS, "Job has been added successfully");
    }

    @Transactional(readOnly = true)
    public List<Job> getAllJob(){
        return jobRepository.findAll();
    }
    public List<Job> getAllJobByUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName() ;
        User users = userRepository.findByUsername(name).orElseThrow();
        return jobRepository.findByUserId(users.getId());
    }

    @Transactional
    public MessageResponse updateJob(Long id, Job updatedJob){
        if(jobRepository.existsByURL(updatedJob.getURL())){
            return new MessageResponse(ResponseType.WARNING, "Cannot change this part");
        }
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));

        job.update(updatedJob);
        jobRepository.save(job);

        return new MessageResponse(ResponseType.SUCCESS, "Job has been updated successfully");
    }

    @Transactional
    public MessageResponse deleteJob(Long id){
        jobRepository.deleteById(id);
        return  new MessageResponse(ResponseType.SUCCESS, "Job has been deleted successfully");
    }

    @Transactional(readOnly = true)
    public Map<String, JobStatusCount> getByIdResult(Long id) {
        Map<String, JobStatusCount> name = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        name.put(now.minusMinutes(1).toString(), jobStatusRepository.findHealthBetweenByJobId(now.minusMinutes(1), now, id));
        for(int i=2; i<4; i++){
            name.put(now.minusMinutes(i).toString(),jobStatusRepository.findHealthBetweenByJobId(now.minusMinutes(i), now.minusMinutes(i-1), id));
        }

        return name;
    }
}
