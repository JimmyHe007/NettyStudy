package chapter_15.server.handle;

import chapter_15.common.entity.MessageRequestPacket;
import chapter_15.common.entity.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author jimmy
 * @create 2019-01-14 17:29
 * @desc 消息请求处理器
 **/
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        System.out.println(new Date()+": 收到客户端消息: "+messageRequestPacket.getMessage());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复: ["+messageRequestPacket.getMessage()+"]");
        channelHandlerContext.channel().write(messageResponsePacket);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
//        ctx.flush(); //两者的区别?
        super.channelReadComplete(ctx);
    }
}
