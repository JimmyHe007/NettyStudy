package chapter_11.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author jimmy
 * @create 2019-01-14 15:46
 * @desc 逻辑处理器A
 **/
public class InBoundHandlerA extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerA: "+msg);
        super.channelRead(ctx, msg);
    }
}
