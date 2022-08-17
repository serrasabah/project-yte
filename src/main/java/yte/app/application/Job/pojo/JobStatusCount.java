package yte.app.application.Job.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobStatusCount {

    private Long reachable = 0L;
    private Long unreachable = 0L;
}
