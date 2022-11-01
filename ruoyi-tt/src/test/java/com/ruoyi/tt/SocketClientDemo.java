package com.ruoyi.tt;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class SocketClientDemo {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8888;
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            socket.setKeepAlive(true);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            // 客户端发送消息
            String msg = "我是客户端！";
            byte[] requestBytes = msg.getBytes();
            out.write(requestBytes.length);
            out.write(requestBytes);
            out.flush();
            // 客户端接收消息
            byte[] bytes = new byte[4];
            in.read(bytes);
            System.out.println("响应消息为：" + new String(bytes));
            //in.read()
            out.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
