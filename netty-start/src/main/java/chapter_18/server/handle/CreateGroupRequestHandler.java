package chapter_18.server.handle;

import chapter_18.common.entity.CreateGroupRequestPacket;
import chapter_18.common.entity.CreateGroupResponsePacket;
import chapter_18.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author jimmy
 * @create 2019-01-18 14:51
 * @desc 创建群聊请求处理器
 **/
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> userIdList = createGroupRequestPacket.getUserIdList();
        List<String> usernameList = new ArrayList<>();

        ChannelGroup group = new DefaultChannelGroup(channelHandlerContext.executor());

        for (String userId:userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                group.add(channel);
                usernameList.add(SessionUtil.getSession(channel).getUsername());
            }
        }

        CreateGroupResponsePacket packet = new CreateGroupResponsePacket();
        packet.setGroupId(UUID.randomUUID().toString());
        SessionUtil.bindChannelGroup(packet.getGroupId(), group);
        packet.setSuccess(true);
        packet.setUsernameList(usernameList);

        group.writeAndFlush(packet);

        System.out.print("群聊创建成功, id为["+packet.getGroupId()+"], 群里面有: "+packet.getUsernameList()+"\n");

    }
}
