package com.ruoyi.tt.service;

import com.ruoyi.common.utils.DateUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author xxxx
 * @version 1.0
 * @date 2020/8/21 20:58
 */
public class SocketServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SocketServerHandler.class);
    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static List<String> msgs = new CopyOnWriteArrayList();
    static {
        msgs.add(DateUtils.getTime()+"："+"测试消息，请忽略(保留最近30条)！！！");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("{},{}",ctx.channel().remoteAddress(),msg);
        msgs.add(0,DateUtils.getTime() + "：" + msg.toString());
        if(msgs.size()>30){
            msgs.remove(msgs.size()-1);
        }
        ctx.channel().writeAndFlush("from socketServer,"+ msg);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress address = channel.remoteAddress();
        log.info("{},-----上线",address);
        group.writeAndFlush(address + "------上线\n");
        group.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress address = channel.remoteAddress();
        log.info("{},-----下线，在线人数：{}",address,group.size());
        group.writeAndFlush(address + "------下线，在线人数：" + group.size() + "\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
