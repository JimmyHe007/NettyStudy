package chapter_20.server.handle;

import chapter_20.common.entity.SendToGroupRequestPacket;
import chapter_20.common.entity.SendToGroupResponsePacket;
import chapter_20.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author jimmy
 * @create 2019-02-21 19:38
 * @desc 群聊消息响应处理器
 **/
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

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
