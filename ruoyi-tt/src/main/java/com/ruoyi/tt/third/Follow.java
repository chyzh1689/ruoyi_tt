package com.ruoyi.tt.third;

import lombok.Data;

@Data
public class Follow {
    private Integer sex;
    private Integer minAge;
    private Integer maxAge;
    private Integer number;
    private Long minSpeed;
    private Long maxSpeed;
    private Long sleepTime;
    private Integer sleepCount;
    private Match match;
}
