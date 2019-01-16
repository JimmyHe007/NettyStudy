package chapter_11.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author jimmy
 * @create 2019-01-14 15:46
 * @desc 逻辑处理器A
 **/
public class OutBoundHandlerB extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("OutBoundHandlerB: "+msg);
        super.write(ctx, msg, promise);
    }
}
