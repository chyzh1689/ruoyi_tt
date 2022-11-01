package com.ruoyi.tt.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * SimpleChannelInboundHandler中的channelRead()方法会自动释放接收到的来自于对方的msg
 * 所占有的所有资源。
 * ChannelInboundHandlerAdapter 中的 channelRead()方法不会自动释放接收到的来自于对方的
 * msg
 * @author xxxx
 * @version 1.0
 * @date 2020/8/21 20:54
 */
public class SocketClientHandler extends SimpleChannelInboundHandler {

    private static final Logger log = LoggerFactory.getLogger(SocketClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("{},{}",ctx.channel().remoteAddress(),msg);
        ctx.channel().writeAndFlush("from socketClient：" + LocalDate.now());
        TimeUnit.MILLISECONDS.sleep(500);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("from client：begin talking!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
