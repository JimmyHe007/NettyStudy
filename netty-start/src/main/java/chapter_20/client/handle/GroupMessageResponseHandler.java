package chapter_20.client.handle;

import chapter_20.common.entity.SendToGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jimmy
 * @create 2019-02-21 19:44
 * @desc 群消息响应处理器
 **/
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendToGroupResponsePacket sendToGroupResponsePacket) throws Exception {

        System.out.print("来自群聊["+sendToGroupResponsePacket.getGroupId()+"]中的["+sendToGroupResponsePacket.getUser().getUsername()+"]的消息: ");
        System.out.println(sendToGroupResponsePacket.getMessage());

    }
}
