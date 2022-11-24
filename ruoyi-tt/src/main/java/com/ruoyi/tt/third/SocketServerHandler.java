package com.ruoyi.tt.third;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author xxxx
 * @version 1.0
 * @date 2020/8/21 20:58
 */
public class SocketServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SocketServerHandler.class);
    public static List<String> msgs = new CopyOnWriteArrayList();
    /***连接保存**/
    private static ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, String> deviceToChannel = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, String> channelToDevice = new ConcurrentHashMap<>();

    static {
        msgs.add(DateUtils.getTime() + "：" + "测试消息，请忽略(保留最近30条)！！！");
    }

    public static ConcurrentHashMap<String, Channel> getChannelMap() {
        return channelMap;
    }

    public static ConcurrentHashMap<String, String> getDeviceToChannel() {
        return deviceToChannel;
    }
    public static ConcurrentHashMap<String, String> getChannelToDevice() {
        return channelToDevice;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        log.info("{},{},{}", channel.id().toString(), ctx.channel().remoteAddress(), msg);
        msgs.add(0, DateUtils.getTime() + "：" + msg.toString());
        if (msgs.size() > 30) {
            msgs.remove(msgs.size() - 1);
        }
        TtSocketService ttSocketService = SpringUtils.getBean(TtSocketService.class);
        R r = ttSocketService.handle(msg, channel.id().toString());
        ctx.channel().writeAndFlush(JSONObject.toJSONString(r));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress address = channel.remoteAddress();
        log.info("{},-----上线", address);
        //channel.writeAndFlush(JSONObject.toJSONString(R.ok(channel.id().toString())));
        channelMap.put(channel.id().toString(), channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress address = channel.remoteAddress();
        log.info("{},-----下线", address);
        channelMap.remove(channel.id().toString());
        //String deviceNo = channelToDevice.remove(channel.id().toString());
        //deviceToChannel.remove(deviceNo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
