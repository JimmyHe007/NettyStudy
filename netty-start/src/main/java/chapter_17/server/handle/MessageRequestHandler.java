package chapter_17.server.handle;

import chapter_17.common.entity.MessageRequestPacket;
import chapter_17.common.entity.MessageResponsePacket;
import chapter_17.common.entity.Session;
import chapter_17.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jimmy
 * @create 2019-01-14 17:29
 * @desc 消息请求处理器
 **/
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        Session session = SessionUtil.getSession(channelHandlerContext.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUsername());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            messageResponsePacket.setMessage("["+messageRequestPacket.getToUserId()+"] 不在线, 发送失败!");
            channelHandlerContext.channel().writeAndFlush(messageResponsePacket);
            System.err.println("["+messageRequestPacket.getToUserId()+"] 不在线, 发送失败!");
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
//        ctx.flush(); //两者的区别?
        super.channelReadComplete(ctx);
    }
}
