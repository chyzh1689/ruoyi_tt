package com.ruoyi.tt.socket;


import com.ruoyi.tt.socket.model.TCPPacket;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class TCPThread extends Thread
{
    public Socket socket;
    public InputStream inputStream;
    public OutputStream outputStream;
    public final ExecutorService threadExecutor = Executors.newCachedThreadPool();

    public final long getTime()
    {
        return System.currentTimeMillis();
    }
    public final String getVersion()
    {
        return "1.0.0";
    }

    private byte[] toBytes(int value)
    {
        return new byte[]{
                (byte) value,
                (byte) (value >>> 8),
                (byte) (value >>> 16),
                (byte) (value >>> 24)
        };
    }
    private int toInteger(byte[] bytes)
    {
        return (bytes[0] & 0xff)
                |((bytes[1] & 0xff) << 8)
                |((bytes[2] & 0xff) << 16)
                |((bytes[3] & 0xff) << 24);
    }

    public abstract void onFailure(Throwable e);

    public Socket getSocket()
    {
        return socket;
    }
    public boolean isConnected()
    {
        return socket != null && socket.isConnected();
    }
    public InputStream getInputStream()
    {
        return inputStream;
    }
    public OutputStream getOutputStream()
    {
        return outputStream;
    }
//
//    public final TCPTask submit(TCPTask task)
//    {
//        threadExecutor.submit(task);
//        return task;
//    }
    public final void submit(Runnable runnable)
    {
        threadExecutor.submit(runnable);
    }
    public final boolean send(TCPPacket packet)
    {
        packet.put("tcp-id", getId());
        packet.put("tcp-time", getTime());
        packet.put("tcp-version", getVersion());
        while (true)
        {
            if (getSocket() == null)
                continue;
            else if (getInputStream() == null)
                continue;
            else if (getOutputStream() == null)
                continue;
            else
                return send(packet.toString(), packet.packet);
        }
    }
    private boolean send(String header, byte[] buffer)
    {
        try
        {
            getOutputStream().write(toBytes(header.getBytes().length));
            getOutputStream().write(header.getBytes());
            getOutputStream().flush();
            if (buffer.length > 0)
            {
                getOutputStream().write(buffer);
                getOutputStream().flush();
            }
            return true;
        }
        catch (Throwable e)
        {
            onFailure(new Exception("send error [" + e + "]"));
        }
        return false;
    }
    private String parseHeader() throws Exception
    {
        byte[] bytes = new byte[4];
        inputStream.read(bytes, 0, bytes.length);
        final int length = toInteger(bytes);
        if (length < 0)
        {
            throw new Exception("parseHeader length = " + length);
        }
        else
        {
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            String header = new String(buffer);
            return header;
        }
    }
    public final TCPPacket parsePacket() throws Exception
    {
        return parsePacket(new TCPPacket(parseHeader()));
    }
    private TCPPacket parsePacket(TCPPacket packet) throws Exception
    {
        return packet.setPacket(parseStream(packet, packet.getPacketLength()));
    }
    private byte[] parseStream(TCPPacket packet, int packetLength) throws Exception
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while (packetLength > 0 && outputStream.size() != packet.getPacketLength())
        {
            int readLength = 1024;
            if (outputStream.size() + readLength > packetLength)
            {
                readLength = packetLength - outputStream.size();
            }
            if (readLength > 0)
            {
                byte[] buffer = new byte[readLength];
                int len = inputStream.read(buffer);
                if (len == -1 || len == 0)
                    throw new Exception("parseStream len = " + len);
                else
                    outputStream.write(buffer, 0, len);
            }
        }
        return outputStream.toByteArray();
    }
    public final void close()
    {
        try
        {
            assert null != getOutputStream();
            getOutputStream().close();
            assert null != getInputStream();
            getInputStream().close();
            assert null != getSocket();
            getSocket().close();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
