package com.ruoyi.tt.third;

import lombok.Data;

@Data
public class UserInfo {
    private String userId;    //用户ID
    private String userName;  //用户昵称
    private String channel;   //分身ID
    private String androidId; //设备ID
    private String imageUrl; //头像地址
}
