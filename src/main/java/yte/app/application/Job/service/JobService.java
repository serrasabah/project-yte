package yte.app.application.Job.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yte.app.application.Job.Entity.Job;
import yte.app.application.authentication.entity.Users;
import yte.app.application.authentication.repository.UserRepository;
import yte.app.application.common.response.MessageResponse;
import yte.app.application.common.response.ResponseType;
import yte.app.application.Job.repository.JobRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository ;

    public MessageResponse addJob(Job job){
        String name = SecurityContextHolder.getContext().getAuthentication().getName() ;
      Users users = userRepository.findByUsername(name).orElseThrow();

      /* if(jobRepository.existsByJobName(job.getJobName()) || jobRepository.existsByURL(job.getURL())){
           return new MessageResponse(ResponseType.WARNING, "JobName and URL are already exist");

       }

       */
        users.addJob(job);
       userRepository.save(users);
        return new MessageResponse(ResponseType.SUCCESS, "Job has been added successfully");
    }

    public List<Job> getAllJob(){

        return jobRepository.findAll();
    }

    public MessageResponse updateJob(Long id, Job updatedJob){

        if(jobRepository.existsByJobName(updatedJob.getJobName())){
            return new MessageResponse(ResponseType.WARNING, "JobName is already exist");

        }

        if(jobRepository.existsByURL(updatedJob.getURL())){
            return new MessageResponse(ResponseType.WARNING, "Cannot change this part");

        }

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));


        job.update(updatedJob);

        jobRepository.save(job);

        return new MessageResponse(ResponseType.SUCCESS, "Job has been updated successfully");
    }

    public MessageResponse deleteJob(Long id){
        jobRepository.deleteById(id);

        return  new MessageResponse(ResponseType.ERROR, "Job has been deleted successfully");
    }

}
