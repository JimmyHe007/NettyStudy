package chapter_19.server.handle;

import chapter_19.common.entity.ListGroupMembersRequestPacket;
import chapter_19.common.entity.ListGroupMembersResponsePacket;
import chapter_19.common.entity.Session;
import chapter_19.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jimmy
 * @create 2019-02-18 10:48
 * @desc 列表展示群组用户请求包处理器
 **/
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMembersRequestPacket listGroupMembersRequestPacket) throws Exception {

        String groupId = listGroupMembersRequestPacket.getGroupId();
        ChannelGroup group = SessionUtil.getChannelGroup(groupId);

        Map<String, String> users = new HashMap<>();
        for (Channel channel : group) {
            Session session = SessionUtil.getSession(channel);
            users.put(session.getUserId(), session.getUsername());
        }

        ListGroupMembersResponsePacket packet = new ListGroupMembersResponsePacket();
        packet.setUsers(users);
        packet.setGroupId(groupId);

        channelHandlerContext.channel().writeAndFlush(packet);

    }

}
