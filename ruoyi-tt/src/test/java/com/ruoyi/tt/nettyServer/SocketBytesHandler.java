package com.ruoyi.tt.nettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketBytesHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("1、handlerAdded()");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("2、channelRegistered()");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("3、channelActive()");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("4、channelRead()");
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("byteBuf=" + byteBuf);
        int requestLength = byteBuf.readByte();
        byte[] bytes = new byte[requestLength];
        byteBuf.readBytes(bytes);
        System.out.println("接收字符串：" + new String(bytes));
        // 写数据到客户端
        String response = "我是服务器端，你要怎么滴？";
        byte[] responseBytes = response.getBytes();
        ByteBuf responseByteBuf = Unpooled.copiedBuffer(responseBytes);
        byte[] lengthBytes = {(byte) responseBytes.length};
        ByteBuf resLengByteBuf = Unpooled.copiedBuffer(lengthBytes);
        ctx.write(resLengByteBuf);
        ctx.writeAndFlush(responseByteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("5、channelReadComplete()");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("6、channelInactive()");
        super.channelInactive(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("7、handlerRemoved()");
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught()");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered()");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelWritabilityChanged()");
        super.channelWritabilityChanged(ctx);
    }

}