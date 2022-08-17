package yte.app.application.Job.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yte.app.application.Job.Entity.Job;
import yte.app.application.Job.Entity.JobStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {
    long countByReachable(Boolean name);

   // Optional<Job> findByJobName(LocalDateTime jobName);
   // List<JobStatus> findCreatedDateBetween(LocalDateTime value1, LocalDateTime value2);
  // Optional<JobStatus> findCreatedDate(Long id);

    @Query(value = "SELECT s.createdDate FROM JobStatus s where s.id = :id")
    LocalDateTime getDate(Long id);
    @Query(value = "SELECT s.id FROM JobStatus s inner join s.job p where p.id = :id")
        List<Long> retrieveJobStatusByJobId(Long id, Sort sort);

    @Query(value = "SELECT count(s.reachable) FROM JobStatus s where s.job.id = :id and s.reachable = true and s.createdDate BETWEEN :startDate and :endDate" )
    long countByHealthy(Long id, @Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT count(s.reachable) FROM JobStatus s where s.job.id = :id and s.reachable = false and s.createdDate BETWEEN :startDate and :endDate" )
    long countByUnHealthy(Long id, @Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT count(s.reachable) FROM JobStatus s where s.job.id = :id and s.reachable = false" )
    long countByAllUnHealthy(Long id);

    @Query(value = "SELECT count(s.reachable) FROM JobStatus s where s.job.id = :id and s.reachable = true" )
    long countByAllHealthy(Long id);

}
