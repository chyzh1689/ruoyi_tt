package com.ruoyi.tt.socket;


import com.ruoyi.tt.socket.model.TCPPacket;

import java.net.Socket;

public abstract class TCPService<T extends TCPServer<T, S>, S extends TCPService<T, S>> extends TCPThread
{
    public T server;
    public String serviceId;
    public TCPService(T server, Socket socket) throws Throwable
    {
        this.server = server;
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
        this.serviceId = getClass().getName() + "@" + getId();
    }
    public abstract void onHandler(TCPPacket request) throws Throwable;

    @Override
    public void onFailure(Throwable e)
    {
        server.getServer().remove(serviceId);
    }
}
