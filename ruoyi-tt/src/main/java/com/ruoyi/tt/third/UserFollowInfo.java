package com.ruoyi.tt.third;

import lombok.Data;

/**
 * 用户关注粉丝信息
 */
@Data
public class UserFollowInfo {
    private String userId;    //用户ID
    private String userName;  //用户昵称
    private String imageUrl; //头像地址
    private String location;//地址
    private String status;
}
