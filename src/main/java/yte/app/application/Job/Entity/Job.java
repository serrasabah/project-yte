package yte.app.application.Job.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import yte.app.application.authentication.entity.User;
import yte.app.application.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@NoArgsConstructor
public class Job extends BaseEntity {

    private String jobName;
    private String URL;
    private Long period;
    private Long timeout;
    public Job(String jobName, String URL, Long period, Long timeout) {
        this.jobName = jobName;
        this.URL = URL;
        this.period = period;
        this.timeout = timeout;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<JobStatus> jobStatus = new ArrayList<>();

    public void update(Job updatedStudent) {
        this.jobName = updatedStudent.jobName;
        this.period = updatedStudent.period;
        this.timeout = updatedStudent.timeout;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
