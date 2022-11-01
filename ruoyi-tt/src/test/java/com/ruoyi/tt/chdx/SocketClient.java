package com.ruoyi.tt.chdx;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient {

    public static void main(String[] args) throws IOException {

        // 建立连接
        Socket socket = new Socket("localhost",8888);
        // 向服务端写数据
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write("我是客户端，收到请回答！！\n");
        bufferedWriter.flush();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = bufferedReader.readLine();
        System.out.println(line);

    }


}

