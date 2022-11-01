package com.ruoyi.tt.socket.web;


import com.ruoyi.tt.debug.JSONObject;
import com.ruoyi.tt.socket.TCPServer;
import com.ruoyi.tt.socket.TCPService;
import com.ruoyi.tt.socket.model.TCPPacket;
import com.ruoyi.tt.socket.model.UserInfo;
import com.ruoyi.tt.socket.web.template.Twitter;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
远程服务端
*/

public class WebServer extends TCPServer<WebServer, WebServer.WebService>
{
    public WebServer(int port)
    {
        super(port);
    }
    @Override
    public WebService accept(Socket socket) throws Throwable
    {
        return new WebService(this, socket);
    }
    public boolean sendMsg(String androidId, TCPPacket request)
    {
        return sendMsg(getServer(androidId), request);
    }
    public boolean sendMsg(WebService service, TCPPacket request)
    {
        if (service == null)
            return false;
        else if (service.userInfo.androidId == null)
            return false;
        else if (service.isConnected())
            return service.send(request);
        return false;
    }
    public void sendMsg(TCPPacket request) //发送到所有已连接的设备
    {
        submit(() -> {
            for (Map.Entry<String, WebService> entry : getServer().entrySet())
            {
                if (entry.getValue().userInfo.androidId == null)
                    continue;
                else
                    sendMsg(entry.getValue(), request);
            }
        });
    }

    @Override
    public void onHandler(WebService service, TCPPacket request) throws Throwable
    {
        JSONObject userinfo = request.optJSONObject("userInfo");
        System.out.println("ssssfsfs");
        if (request.optString("packageName").equals("com.twitter.android"))
        {
            String userId = userinfo.optString("userId");
            if (request.action().equals("client-init"))
            {
                //分身用户初始化
                Twitter twitter = new Twitter(this);
                twitter.userInfo = new UserInfo(userinfo);
                //分身用户以 userId 作为身份ID
                service.twitterMap.put(twitter.userInfo.userId, twitter);
            }
            else if (request.action().equals("client-message"))
            {
                if (service.twitterMap.containsKey(userId))
                {
                    Twitter twitter = service.twitterMap.get(userId);
                    if (twitter != null)
                    {
                        //分身用户最后一次心跳时间
                        twitter.time = request.optLong("tcp-time");
                        twitter.onHandler(service, request.optJSONObject("message"));
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Throwable e)
    {
        super.onFailure(e);
    }

    public  class WebService extends TCPService<WebServer, WebService>
    {
        public long time = 0;
        //主用户
        public UserInfo userInfo = new UserInfo();
        //分身用户
        public Map<String, Twitter> twitterMap = new ConcurrentHashMap<>();

        public WebService(WebServer server, Socket socket) throws Throwable
        {
           super(server, socket);
        }
        @Override
        public void run()
        {
            try
            {
                while (socket.isConnected())
                {
                    onHandler(parsePacket());
                }
            }
            catch (Throwable e)
            {
                onFailure(new Exception("run error [" + e + "]"));
            }
            finally
            {
                close();
            }
        }
        @Override
        public void onFailure(Throwable e)
        {
            super.onFailure(e);
        }
        @Override
        public void onHandler(TCPPacket request) throws Throwable
        {
            System.out.println("tcp");
            if (request.optString("packageName").equals("admin"))
            {
                if (request.action().equals("client-sync"))
                {
                    //主用户最后一次心跳时间
                    time = request.optLong("tcp-time");
                }
                else if (request.action().equals("client-init"))
                {
                    //主用户初始化
                    userInfo = new UserInfo(request.optJSONObject("userInfo"));
                    //主用户以 androidId 作为身份ID
                    server.putServer(serviceId = userInfo.androidId, this);
                }
            }
            else
            {
                server.onHandler(this, request);
            }
        }
    }
}
