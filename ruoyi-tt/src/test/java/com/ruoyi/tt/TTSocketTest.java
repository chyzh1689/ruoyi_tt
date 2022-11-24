package com.ruoyi.tt;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.tt.third.TTScoketConstants;
import com.ruoyi.tt.third.TTSocketDto;
import com.ruoyi.tt.third.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

@Slf4j
public class TTSocketTest {
//    String host = "localhost";
    String host = "139.198.40.36";
    int port = 8011;

    /***
     * 设备登录
     */
    @Test
    public void deviceLogin(){
        //设备登录
        TTSocketDto ttSocketDto = new TTSocketDto();
        ttSocketDto.setAction(TTScoketConstants.ACTION_CLIENT_INIT);
        ttSocketDto.setPackageName(TTScoketConstants.PACKAGE_NAME_ADMIN);
        ttSocketDto.setAndroidId("24242");
        this.commonSend(ttSocketDto);
        //账号登录

    }

    /***
     * 账号登录
     */
    @Test
    public void accountLogin(){
        TTSocketDto ttSocketDto = new TTSocketDto();
        ttSocketDto.setAction(TTScoketConstants.ACTION_CLIENT_INIT);
        ttSocketDto.setPackageName(TTScoketConstants.PACKAGE_NAME_TT);
        ttSocketDto.setAndroidId("24242");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("123");
        userInfo.setUserName("u_name");
        userInfo.setImageUrl("image_url");
        ttSocketDto.setUserInfo(userInfo);
        this.commonSend(ttSocketDto);
    }




    private void commonSend(TTSocketDto ttSocketDto) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            socket.setKeepAlive(true);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            // 客户端发送消息
            ByteBuffer header = ByteBuffer.allocate(4);
            String msg = JSONObject.toJSONString(ttSocketDto);
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
