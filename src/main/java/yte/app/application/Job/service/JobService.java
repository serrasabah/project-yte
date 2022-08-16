package yte.app.application.Job.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yte.app.application.Job.Entity.Job;
import yte.app.application.Job.Entity.JobStatus;
import yte.app.application.Job.repository.JobStatusRepository;
import yte.app.application.authentication.entity.User;
import yte.app.application.authentication.repository.UserRepository;
import yte.app.application.common.response.MessageResponse;
import yte.app.application.common.response.ResponseType;
import yte.app.application.Job.repository.JobRepository;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;


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
       // String name = SecurityContextHolder.getContext().getAuthentication().getName() ;
       // User users = userRepository.findByUsername(name).orElseThrow();
        //return jobRepository.findByUserId(users.getId());
        return jobRepository.findAll();
    }
    public List<Job> getAllJobByUser(){
         String name = SecurityContextHolder.getContext().getAuthentication().getName() ;
        User users = userRepository.findByUsername(name).orElseThrow();
        return jobRepository.findByUserId(users.getId());

    }


    @Transactional
    public MessageResponse updateJob(Long id, Job updatedJob){

        /*
        if(jobRepository.existsByJobName(updatedJob.getJobName())){
            return new MessageResponse(ResponseType.WARNING, "JobName is already exist");

        }

         */

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

    public Job getById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow();
        //job.setHealthy(jobStatusRepository.countByReachable(true));
        job.setUnhealthy(jobStatusRepository.countByReachable(false));
       List<Long> status = jobStatusRepository.retrieveJobStatusByJobId(id);
        long statusFirst = status.get(0);
        System.out.println("statusFirst"+statusFirst);
        LocalDateTime start = jobStatusRepository.getDate(statusFirst);
        LocalDateTime end = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(),start.getHour(),start.getMinute() + 1, start.getSecond());
        System.out.println("end date" + end);
        //hata
        job.setHealthy(jobStatusRepository.findHealthBetween(start,end));
        System.out.println(job.getHealthy());
      // System.out.println(jobStatusRepository.findCreatedDateBetween());
        return jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));
    }

}
