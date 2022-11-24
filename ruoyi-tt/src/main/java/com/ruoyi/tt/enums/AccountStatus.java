package com.ruoyi.tt.enums;

/**
 * 商户类型 0-商户 1-员工
 */
public enum AccountStatus {
    UNLOGIN("0"),
    LOGIN("1");
    private String val;

    AccountStatus(String val){
        this.val= val;
    }

    public String val(){
        return val;
    }
}
