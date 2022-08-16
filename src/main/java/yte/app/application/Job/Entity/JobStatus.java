package yte.app.application.Job.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.app.application.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@NoArgsConstructor
public class JobStatus extends BaseEntity {

    private boolean reachable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    public void setJob(Job job) {
        this.job = job;
    }


}
