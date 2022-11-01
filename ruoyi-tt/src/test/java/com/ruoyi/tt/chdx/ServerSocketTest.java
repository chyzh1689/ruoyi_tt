package com.ruoyi.tt.chdx;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketTest {
    public static void main(String[] args) {
        List<Socket> sockets = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(8866);
            while (true){
                Socket scoket = serverSocket.accept();
                sockets.add(scoket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
