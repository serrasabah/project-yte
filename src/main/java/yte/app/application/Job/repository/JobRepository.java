package yte.app.application.Job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.app.application.Job.Entity.Job;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsByJobName(String jobName);
    boolean existsByURL(String URL);

    boolean existsByPeriod(Long period);
    boolean existsBytimeout(Long tiemout);


    Optional<Job> findByJobName(String jobName);

    Optional<Job> findByPeriod(Long jobName);
    List<Job> findByUserId(Long id);


}
