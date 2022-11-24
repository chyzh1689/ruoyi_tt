package com.ruoyi.tt.third;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

@Data
public class TTSocketResp {
    private String action;
    private Object message;
    private UserInfo userInfo;
    private String packageName;
}
