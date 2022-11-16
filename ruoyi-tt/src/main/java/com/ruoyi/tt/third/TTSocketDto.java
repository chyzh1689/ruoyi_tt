package com.ruoyi.tt.third;

import lombok.Data;

@Data
public class TTSocketDto {
    private Long tcpId;
    private Long tcpTime;
    private String tcpVersion;
    private String androidId;
    private String messageId;
    private String packageName;
    private String action;
    private UserInfo userInfo;
    private String message;

}
