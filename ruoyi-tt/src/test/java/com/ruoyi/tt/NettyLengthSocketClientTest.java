package com.ruoyi.tt;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

@Slf4j
public class NettyLengthSocketClientTest {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8888;
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            socket.setKeepAlive(true);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            // 客户端发送消息
            ByteBuffer header = ByteBuffer.allocate(4);
            String msg = "我是客户端！";
            byte[] msgBytes = msg.getBytes();
            header.putInt(msgBytes.length);
            out.write(header.array());
            out.write(msgBytes);
            out.flush();

            byte[] responseLengthBytes = new byte[4];
            int readI = in.read(responseLengthBytes);
            System.out.println("已读长度为：" + readI);
            // 客户端接收消息
            byte[] responseContentBytes = new byte[1024];
            int readed = in.read(responseContentBytes);
            if (readed > 0) {
                System.out.println("已读长度为：" + readed);
                String responseContent = new String(responseContentBytes, 0, readed);
                System.out.println("服务器端响应内容：" + responseContent);
            }
            out.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
