package yte.app.application.Job.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import yte.app.application.authentication.entity.Users;
import yte.app.application.common.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@Getter
@NoArgsConstructor
public class Job extends BaseEntity {

    private String jobName;
    private String URL;
    private Long period;
    private Long timeout;
    private Long healthy = 0L;
    private Long unhealthy = 0L;

    public Job(String jobName, String URL, Long period, Long timeout) {
        this.jobName = jobName;
        this.URL = URL;
        this.period = period;
        this.timeout = timeout;
    }

   /* @ManyToOne
    @JoinColumn(name = "job_id")
    private Set<Admin> users = new HashSet<>();

  */
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id")
   private Users user;


    public void update(Job updatedStudent) {
        this.jobName = updatedStudent.jobName;
        this.period = updatedStudent.period;
        this.timeout = updatedStudent.timeout;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
