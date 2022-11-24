package com.ruoyi;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.tt.third.SocketServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication{

    private static final Logger log = LoggerFactory.getLogger(RuoYiApplication.class);
    public static void main(String[] args)  {
        SpringApplication.run(RuoYiApplication.class, args);
        log.info("后台管理服务启动成功！");
        EventLoopGroup parent = new NioEventLoopGroup();
        EventLoopGroup children = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(parent, children).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4))
                                    .addLast(new LengthFieldPrepender(4))
                                    .addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8))
                                    .addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8))
                                    .addLast("socketServerHandler",new SocketServerHandler());
                        }
                    });
            int ttPort = Integer.parseInt(SpringUtils.getRequiredProperty("ruoyi.ttPort"));
            ChannelFuture future = bootstrap.bind(ttPort).sync();
            log.info("{},后台通信服务已在端口 {} 启动", RuoYiApplication.class.getSimpleName(),ttPort);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parent.shutdownGracefully();
            children.shutdownGracefully();
        }
    }
}