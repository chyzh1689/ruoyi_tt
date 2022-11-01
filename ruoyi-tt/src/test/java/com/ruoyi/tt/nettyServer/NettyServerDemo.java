package com.ruoyi.tt.nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerDemo {

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap server = new ServerBootstrap();
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        server.group(parentGroup, childGroup);
        server.option(ChannelOption.SO_BACKLOG, 128);
        server.channel(NioServerSocketChannel.class);
        server.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new SocketBytesHandler());
            }
        });
        ChannelFuture future = server.bind(11111).sync();
        future.channel().closeFuture().sync();
    }

}