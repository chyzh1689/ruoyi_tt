package com.ruoyi.tt.third;

import lombok.Data;

@Data
public class Chat {
    private String contents;
    private Integer sendCount;
    private Long sleepTime;
}
