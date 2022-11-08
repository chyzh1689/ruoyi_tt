package com.ruoyi.tt.enums;

/**
 * 可以渠道
 */
public enum ChannelPackage {
    TT(1),
    FB(2);
    private int val;
    ChannelPackage (Integer val){
        this.val= val;
    }

    public Integer val(){
        return val;
    }


}
