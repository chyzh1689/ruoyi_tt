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
    String host = "localhost";
//    String host = "139.198.40.36";
    int port = 8011;

    @Test
    public void interface_test(){
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            socket.setKeepAlive(true);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            //登录设备
            sendAndResp(this.deviceLogin(), out, in);
            //发送关注列表
            sendAndResp(this.followList(),out,in);

            out.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /***
     * 询问关注列表
     */
    public TTSocketDto followList(){
        TTSocketDto ttSocketDto = new TTSocketDto();
        ttSocketDto.setAction(TTScoketConstants.ACTION_follow_list);
        ttSocketDto.setPackageName(TTScoketConstants.PACKAGE_NAME_TT);
        ttSocketDto.setUserInfo(new UserInfo());
        ttSocketDto.setAndroidId("24242");
        ttSocketDto.setData(new String[]{"123232"});
        return ttSocketDto;
    }
    /***
     * 获取配置信息
     */
    public TTSocketDto appConfig(){
        //设备登录
        TTSocketDto ttSocketDto = new TTSocketDto();
        ttSocketDto.setAction(TTScoketConstants.ACTION_APP_CONFIG);
        ttSocketDto.setPackageName(TTScoketConstants.PACKAGE_NAME_TT);
        ttSocketDto.setUserInfo(new UserInfo());
        ttSocketDto.setAndroidId("24242");
        return ttSocketDto;
    }
    /***
     * 设备登录
     */
    public TTSocketDto deviceLogin(){
        //设备登录
        TTSocketDto ttSocketDto = new TTSocketDto();
        ttSocketDto.setAction(TTScoketConstants.ACTION_DEVICE_LOGIN);
        ttSocketDto.setPackageName(TTScoketConstants.PACKAGE_NAME_ADMIN);
        ttSocketDto.setAndroidId("24242");
        return ttSocketDto;

    }

    /***
     * 账号登录
     */
    public TTSocketDto accountLogin(){
        TTSocketDto ttSocketDto = new TTSocketDto();
        ttSocketDto.setAction(TTScoketConstants.ACTION_ACCOUNT_LOGIN);
        ttSocketDto.setPackageName(TTScoketConstants.PACKAGE_NAME_TT);
        ttSocketDto.setAndroidId("24242");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("123");
        userInfo.setUserName("u_name");
        userInfo.setImageUrl("image_url");
        ttSocketDto.setUserInfo(userInfo);
        return ttSocketDto;
    }



    private void sendAndResp(TTSocketDto ttSocketDto, OutputStream out, InputStream in) throws IOException {
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
    }
}
