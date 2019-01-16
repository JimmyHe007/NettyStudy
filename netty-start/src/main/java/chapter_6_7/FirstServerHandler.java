package chapter_6_7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author jimmy
 * @create 2019-01-10 15:18
 * @desc 服务端逻辑处理器
 **/
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buffer = (ByteBuf) msg;
        System.out.println(new Date() + ": 服务端读取数据 ->" + buffer.toString(Charset.forName("UTF-8")));

        System.out.println(new Date() + ": 服务端写出数据");
        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "Welcome to the world!".getBytes(Charset.forName("UTF-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        return buffer;
    }

}
