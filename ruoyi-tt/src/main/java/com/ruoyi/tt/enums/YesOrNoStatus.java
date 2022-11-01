package com.ruoyi.tt.enums;

/**
 * 是或者不是、禁用或者不禁用
 */
public enum YesOrNoStatus {
    YES("0"),
    No("1");
    private String val;

    YesOrNoStatus (String val){
        this.val= val;
    }

    public String val(){
        return val;
    }
}
