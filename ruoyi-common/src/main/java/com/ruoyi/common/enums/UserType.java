package com.ruoyi.common.enums;

/**
 * 业务操作类型
 * 
 * @author ruoyi
 */
public enum UserType{
    SYS("00"),
    MECH("1"),
    EMPL("2");
    private String val;

    UserType (String val){
        this.val= val;
    }

    public String val(){
        return val;
    }
}
