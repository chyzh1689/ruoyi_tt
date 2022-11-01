package com.ruoyi.tt.socket;


import com.ruoyi.tt.socket.model.TCPPacket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class TCPServer<T extends TCPServer<T, S>, S extends TCPService<T, S>> extends TCPThread
{
    private final int port;
    private final Map<String, S> serverMap = new ConcurrentHashMap<>();
    private final List<ServerMonitor<T, S>> serverMonitorList = new ArrayList<>();

    public TCPServer(int port)
    {
        this.port = port;
    }

    public abstract S accept(Socket socket) throws Throwable;
    public abstract void onHandler(S t, TCPPacket request) throws Throwable;

    @Override
    public void onFailure(Throwable e)
    {
        serverMonitorList.clear();
    }

    public void addMonitor(ServerMonitor<T, S> monitor)
    {
        serverMonitorList.add(monitor);
    }
    public List<ServerMonitor<T, S>> getServerMonitorList()
    {
        return serverMonitorList;
    }

    public void putServer(String id, S service)
    {
        serverMap.put(id, service);
    }
    public Map<String, S> getServer()
    {
        return serverMap;
    }
    public S getServer(String id)
    {
        return serverMap.get(id);
    }

    public boolean send(String id, TCPPacket packet)
    {
        if (serverMap.containsKey(id))
        {
            return serverMap.get(id).send(packet);
        }
        return false;
    }
    public void sendAll(TCPPacket packet)
    {
        for (Map.Entry<String, S> service : serverMap.entrySet())
        {
            if (service.getValue().isConnected())
                service.getValue().send(packet);
        }
    }

    @Override
    public void run()
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true)
            {
                try
                {
                    System.out.println("22222---");
                    S server = accept(serverSocket.accept());
                    System.out.println("111---");
                    server.start();
                }
                catch (Throwable e)
                {
                    onFailure(new Exception("run error [" + e + "]"));
                }
            }
        }
        catch (Throwable e)
        {
           onFailure(e);
        }
    }
}
