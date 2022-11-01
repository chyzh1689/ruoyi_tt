package com.ruoyi.tt.socket.web.template;


import com.ruoyi.tt.debug.JSON;
import com.ruoyi.tt.debug.JSONArray;
import com.ruoyi.tt.debug.JSONObject;
import com.ruoyi.tt.socket.model.TCPPacket;
import com.ruoyi.tt.socket.model.UserInfo;
import com.ruoyi.tt.socket.web.WebServer;

public class Twitter
{
    public long time = 0;
    public WebServer server;
    //推特用户信息
    public UserInfo userInfo = new UserInfo();
    public String packageName = "com.twitter.android";

    public Twitter(WebServer server)
    {
        this.server =  server;
    }

    public boolean sendMsg(String action, JSON message)
    {
        return sendMsg(action, message.toString());
    }

    public boolean sendMsg(String action, String message)
    {
        return server.sendMsg(userInfo.androidId, new TCPPacket()
                .action(action)
                .put("message", message)
                .put("packageName", packageName)
                .put("userInfo", userInfo.toString())
        );
    }

    public void onHandler(WebServer.WebService service, JSONObject message) throws Throwable
    {
        final String method = message.optString("method");
        if (method.equals("app-config"))
        {
            JSONObject config = new JSONObject();
            config.put("follow_number", 10);
            config.put("reply_content", new JSONObject()
                    .put("default", "Hi")
                    .put("匹配文字", "回复内容")
            );
            config.put("regions", new JSONArray().put("china"));
            sendMsg(method, config);
        }
        else if (method.equals("app-error"))
        {
            String string = message.optString("message");
        }
        else if (method.equals("app-message"))
        {
            String userId = message.optString("userId");    //发送者userId
            String userName = message.optString("userName");//发送者昵称
            String imageUrl = message.optString("imageUrl");//发送者头像
            String text = message.optString("text");        //发过来的文字
        }
        else if (method.equals("run-error"))
        {
            String string = message.optString("message");
        }
        else if (method.equals("follow-success"))
        {
            String userId = message.optString("userId");
            String userName = message.optString("userName");
            String location = message.optString("location");
            String imageUrl = message.optString("imageUrl");
        }
        else if (method.equals("follow-failure"))
        {
            String userId = message.optString("userId");
            String userName = message.optString("userName");
            String location = message.optString("location");
            String imageUrl = message.optString("imageUrl");
        }
        else if (method.equals("follow-list"))
        //云端 userList 去重
        {
            JSONArray userList = message.optJSONArray("list");
            for (int index = 0 ; index < userList.length() ; index ++)
            {
                JSONObject item = userList.optJSONObject(index);
                String userId = item.optString("userId");
                item.put(userId, true); // false 代表已经存在
            }
            sendMsg(method, userList);
        }
        else if (method.equals("user-list"))
        {
            JSONArray userList = message.optJSONArray("list");
            for (int index = 0 ; index < userList.length() ; index ++)
            {
                JSONObject item = userList.optJSONObject(index);
                String userId = item.optString("userId");
                String userName = item.optString("userName");
                String imageUrl = item.optString("imageUrl");
                String location = item.optString("location");
            }
        }
    }

    public boolean sendFollowList(JSONArray array)
    {
        return sendMsg("send-follow-list", array);
    }
    public boolean sendMessage(String content)
    {
        return sendMsg("send-message", content);
    }
    public boolean sendOpenApp()
    {
        return sendMsg("send-open-app", packageName);
    }
    public boolean sendVibration()
    {
        return sendMsg(userInfo.androidId, new TCPPacket().action("send-vibration"));
    }
}
