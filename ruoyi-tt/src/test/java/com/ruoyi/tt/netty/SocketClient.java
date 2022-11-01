package com.ruoyi.tt.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author xxxx
 * @version 1.0
 * @date 2020/8/21 21:06
 */
public class SocketClient {
    public static void main(String[] args) {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap().group(eventLoopGroup)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline()
                            .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4))
                            .addLast(new LengthFieldPrepender(4))
                            .addLast("StringDecoder",new StringDecoder(CharsetUtil.UTF_8))
                            .addLast("StringEncoder",new StringEncoder(CharsetUtil.UTF_8))
                            .addLast("SocketClientHandler",new SocketClientHandler());
                }
            });
        try {
            ChannelFuture future = bootstrap.connect("localhost", 8888).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(eventLoopGroup!=null){
                eventLoopGroup.shutdownGracefully();
            }
        }

    }
}
