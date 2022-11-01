package com.ruoyi.tt.socket.model;


import com.ruoyi.tt.debug.JSONObject;

public class UserInfo
{
    public String userId = "0";    //用户ID
    public String userName = "lange";  //用户昵称
    public String channel = "0";   //分身ID
    public String androidId = "unknown"; //设备ID
    public UserInfo()
    {

    }
    public UserInfo(JSONObject userinfo)
    {
        this.userId = userinfo.optString("userId");
        this.userName = userinfo.optString("userName");
        this.channel = userinfo.optString("channel");
        this.androidId = userinfo.optString("androidId");
    }
    public JSONObject toJSONObject()
    {
        return new JSONObject()
                .put("userId", userId)
                .put("userName", userName)
                .put("channel", channel)
                .put("androidId", androidId);
    }
    @Override
    public String toString()
    {
        return toJSONObject().toString();
    }
}
