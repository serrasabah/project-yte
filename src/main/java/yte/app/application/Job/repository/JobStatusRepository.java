package yte.app.application.Job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yte.app.application.Job.Entity.JobStatus;
import yte.app.application.Job.pojo.JobStatusCount;

import java.time.LocalDateTime;

public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {
    @Query(value = "SELECT new yte.app.application.Job.pojo.JobStatusCount(sum(s.reachable), sum(s.unreachable)) FROM JobStatus s where s.job.id = :jobId  AND (s.createdDate BETWEEN :startDate AND :endDate)" )
    JobStatusCount findHealthBetweenByJobId(LocalDateTime startDate, LocalDateTime endDate, Long jobId);

}
