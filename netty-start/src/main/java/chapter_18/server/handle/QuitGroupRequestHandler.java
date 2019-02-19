package chapter_18.server.handle;

import chapter_18.common.entity.QuitGroupRequestPacket;
import chapter_18.common.entity.QuitGroupResponsePacket;
import chapter_18.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author jimmy
 * @create 2019-02-18 17:01
 * @desc 退出群组请求包处理器
 **/
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {

        String groupId = quitGroupRequestPacket.getGroupId();
        ChannelGroup group = SessionUtil.getChannelGroup(groupId);
        Boolean success = group.remove(channelHandlerContext.channel());

        QuitGroupResponsePacket packet = new QuitGroupResponsePacket();
        packet.setGroupId(groupId);
        packet.setSuccess(success);

        channelHandlerContext.channel().writeAndFlush(packet);

    }
}
