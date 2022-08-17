package yte.app.application.scheduling;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yte.app.application.Job.Entity.Job;
import yte.app.application.Job.Entity.JobStatus;
import yte.app.application.Job.repository.JobRepository;
import yte.app.application.Job.service.JobService;

import java.net.InetAddress;
import java.util.Optional;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final JobService jobService;
    private final JobRepository jobRepository;
    @Bean
    public ScheduledExecutorService threadPool() {
        return Executors.newScheduledThreadPool(4);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void startScanning() {
        var jobs = jobService.getAllJob();
        for(Job job: jobs) {
            String threadName =  job.getJobName();
            if(isJobExists(threadName)) {
                System.out.println(threadName + " is already exist.");
            } else {
                System.out.println(threadName + " thread created.");
                Runnable task = buildURLScanner(threadName, job);
                threadPool().scheduleAtFixedRate(task, 0, job.getPeriod(), TimeUnit.SECONDS);
            }
        }
    }

    private boolean isJobExists(String threadName) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals(threadName)) {
                return true;
            }
        }
        return false;
    }

    private Runnable buildURLScanner(String threadName, Job job) {
        return () -> {
            try {
                Thread.currentThread().setName(threadName);
                Optional<Job>  storedJob = jobRepository.findById(job.getId());

                boolean result = false;

                try {
                     result = InetAddress.getByName(job.getURL()).isReachable(job.getTimeout().intValue());
                }catch (Exception e){
                    System.out.println("verilen url değerine ulaşılamamaktadır.");
                }
                if(storedJob.isPresent()){
                    JobStatus jobStatus = new JobStatus();
                    if(result==true){
                        jobStatus.setReachable(1L);
                    }
                    else{
                        jobStatus.setUnreachable(1L);
                    }
                    jobStatus.setJob(storedJob.get());
                    storedJob.get().getJobStatus().add(jobStatus);

                    jobRepository.save(storedJob.get());
                    System.out.println(Thread.currentThread().getName() + ":    " + job.getURL() + " isReachable? " + result);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}