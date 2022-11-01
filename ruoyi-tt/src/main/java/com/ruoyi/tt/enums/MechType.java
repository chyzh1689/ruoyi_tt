package com.ruoyi.tt.enums;

/**
 * 商户类型 0-商户 1-员工
 */
public enum MechType {
    MECH("1"),
    EMPL("2");
    private String val;

    MechType (String val){
        this.val= val;
    }

    public String val(){
        return val;
    }
}
