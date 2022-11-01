package com.ruoyi.tt.socket.model;


import com.ruoyi.tt.debug.JSONObject;

import java.io.Closeable;
import java.io.IOException;

public class TCPPacket extends JSONObject implements Closeable
{
    public TCPPacket()
    {
        super();
    }
    public TCPPacket(String json)
    {
        super(json);
    }
    public byte[] packet = new byte[0];
    public String action()
    {
        return optString("action");
    }
    public TCPPacket action(String action)
    {
        return put("action", action);
    }
    public TCPPacket put(String name, Object value)
    {
        super.put(name, value);
        return this;
    }
    public TCPPacket setPacket(String packet)
    {
        return setPacket(packet.getBytes());
    }
    public TCPPacket setPacket(byte[] packet)
    {
        this.packet = packet;
        return success(true);
    }

    public boolean success()
    {
        return optBoolean("success");
    }
    public TCPPacket success(boolean success)
    {
        return put("success", success);
    }

    public String getMessage()
    {
        return optString("message");
    }
    public TCPPacket setMessage(String throwable)
    {
        return success(false).put("message", throwable);
    }
    public TCPPacket setMessage(Throwable throwable)
    {
        return success(false).put("message", throwable.toString());
    }

    public int getPacketLength()
    {
        return optInt("packetLength", 0);
    }

    @Override
    public String toString()
    {
        put("packetLength", packet.length);
        return super.toString();
    }

    @Override
    public void close() throws IOException
    {

    }
}
