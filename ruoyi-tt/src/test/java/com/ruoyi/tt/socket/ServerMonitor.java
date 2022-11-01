package com.ruoyi.tt.socket;


import com.ruoyi.tt.socket.model.TCPPacket;

public abstract class ServerMonitor<T extends TCPServer<T, S>, S extends TCPService<T, S>>
{
    public void onUser(T t)
    {

    }
    public void onHandler(S s, TCPPacket request) throws Throwable
    {

    }
    public void onFailure(T t, Throwable e)
    {

    }
    public void onFailure(S s, Throwable e)
    {

    }
}
