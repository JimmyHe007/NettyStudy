package chapter_19.server.handle;

import chapter_19.common.entity.SendToGroupRequestPacket;
import chapter_19.common.entity.SendToGroupResponsePacket;
import chapter_19.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author jimmy
 * @create 2019-02-21 19:38
 * @desc 群聊消息响应处理器
 **/
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendToGroupRequestPacket sendToGroupRequestPacket) throws Exception {

        String groupId = sendToGroupRequestPacket.getGroupId();
        SendToGroupResponsePacket packet = new SendToGroupResponsePacket();
        packet.setGroupId(groupId);
        packet.setUser(SessionUtil.getSession(channelHandlerContext.channel()));
        packet.setMessage(sendToGroupRequestPacket.getMessage());

        ChannelGroup group = SessionUtil.getChannelGroup(groupId);
        group.writeAndFlush(packet);

    }
}
