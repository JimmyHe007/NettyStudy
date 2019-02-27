package chapter_20.server.handle;

import chapter_20.common.entity.HeartBeatRequestPacket;
import chapter_20.common.entity.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jimmy
 * @create 2019-02-26 10:26
 * @desc 心跳包请求处理器
 **/
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HeartBeatRequestPacket heartBeatRequestPacket) throws Exception {
        System.out.println("成功接收心跳包!");
        channelHandlerContext.writeAndFlush(new HeartBeatResponsePacket());
    }
}
