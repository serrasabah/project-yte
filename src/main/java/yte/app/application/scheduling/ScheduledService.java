package yte.app.application.scheduling;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yte.app.application.Job.Entity.Job;
import yte.app.application.Job.service.JobService;

import java.net.InetAddress;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final JobService jobService;
    private final SessionFactory sessionFactory;

    @Bean
    public ScheduledExecutorService threadPool() {
        return Executors.newScheduledThreadPool(4);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void startScanning() {
        var jobs = jobService.getAllJob();
        for(Job job: jobs) {
            String threadName = "yte-scanner--" + job.getJobName();
            if(isJobExists(threadName)) {
                System.out.println(threadName + " is already exist.");
            } else {
                System.out.println(threadName + " thread created.");
                Runnable task = buildURLScanner(threadName, job);
                threadPool().scheduleAtFixedRate(task, 0, job.getPeriod(), TimeUnit.MILLISECONDS);
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

                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();

                Job storedJob = session.get(Job.class, job.getId());

                boolean result = InetAddress.getByName(job.getURL()).isReachable(job.getTimeout().intValue());
                if(result) {
                    storedJob.setHealthy(storedJob.getHealthy() + 1);
                } else {
                    storedJob.setUnhealthy(storedJob.getUnhealthy() + 1);
                }

                session.save(storedJob);
                transaction.commit();
                session.close();

                System.out.println(Thread.currentThread().getName() + ":    " + job.getURL() + " isReachable? " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}
