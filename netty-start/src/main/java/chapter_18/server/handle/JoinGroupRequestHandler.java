package chapter_18.server.handle;

import chapter_18.common.entity.JoinGroupRequestPacket;
import chapter_18.common.entity.JoinGroupResponsePacket;
import chapter_18.common.entity.Session;
import chapter_18.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jimmy
 * @create 2019-02-18 16:42
 * @desc 加入群组请求包处理器
 **/
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupRequestPacket joinGroupRequestPacket) throws Exception {

        String groupId = joinGroupRequestPacket.getGroupId();
        Session session = SessionUtil.getSession(channelHandlerContext.channel());
        SessionUtil.getChannelGroup(groupId).add(channelHandlerContext.channel());

        JoinGroupResponsePacket packet = new JoinGroupResponsePacket();
        packet.setSuccess(true);
        packet.setGroupId(groupId);
        channelHandlerContext.channel().writeAndFlush(packet);

    }
}
