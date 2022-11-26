package com.ruoyi.tt.enums;

/**
 * 关注状态 0未关注 1已申请 2已关注 3回关
 */
public enum NoticeFlag {
    free(0),
    apply(1),
    notice(2),
    back(3);
    private int val;

    NoticeFlag(int val){
        this.val= val;
    }

    public Integer val(){
        return val;
    }
}
