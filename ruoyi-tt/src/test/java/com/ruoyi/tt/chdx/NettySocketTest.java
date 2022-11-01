package com.ruoyi.tt.chdx;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NettySocketTest {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 8888);
            System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            outToServer.write("2322442422442".getBytes(StandardCharsets.UTF_8));
            outToServer.flush();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
