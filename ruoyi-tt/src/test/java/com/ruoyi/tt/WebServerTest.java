package com.ruoyi.tt;

import com.ruoyi.tt.socket.web.WebServer;

public class WebServerTest {
    public static void main(String[] args) {
        WebServer webServer = new WebServer(8888);
        webServer.start();
    }
}
