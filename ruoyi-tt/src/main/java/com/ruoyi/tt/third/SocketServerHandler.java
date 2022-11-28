package com.ruoyi.tt.third;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.RedisConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.CacheUtils;
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

    static {
        msgs.add(DateUtils.getTime() + "：" + "测试消息，请忽略(保留最近30条)！！！");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        log.info("channel.id:{},{},{}", channel.id().toString(), ctx.channel().remoteAddress(), msg);
        msgs.add(0, DateUtils.getTime() + "：" + msg.toString());
        if (msgs.size() > 30) {
            msgs.remove(msgs.size() - 1);
        }
        TtSocketService ttSocketService = SpringUtils.getBean(TtSocketService.class);
        if(msg instanceof String){
            TTSocketDto ttSocketDto = JSON.parseObject((String) msg, TTSocketDto.class);
            ttSocketDto.setChannelId(channel.id().toString());
            ttSocketDto.setPackageName(TTScoketConstants.getChannelMap().get(ttSocketDto.getPackageName()));
            ttSocketDto = ttSocketService.handle(ttSocketDto);
            ttSocketDto.setPackageName(TTScoketConstants.getChannelMap().get(ttSocketDto.getPackageName()));
            if(TTScoketConstants.ACTION_APP_CONFIG.equals(ttSocketDto.getAction())||
            TTScoketConstants.ACTION_SYNC.equals(ttSocketDto.getAction())||
            TTScoketConstants.ACTION_QUERY_ROOM.equals(ttSocketDto.getAction())||
            TTScoketConstants.ACTION_follow_list.equals(ttSocketDto.getAction())){
            }else{
                ttSocketDto.setAction(null);
                ttSocketDto.setData(null);
            }
            String s = JSONObject.toJSONString(ttSocketDto);
            log.info(ttSocketDto.getAction() +" : s-length:"+s.length());
            ctx.channel().writeAndFlush(s);
        }else{
            ctx.channel().writeAndFlush(JSONObject.toJSONString(R.ok()));
        }


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
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
