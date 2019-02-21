package chapter_19.client.handle;

import chapter_19.common.entity.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jimmy
 * @create 2019-02-18 16:46
 * @desc 加入群组响应包处理器
 **/
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {

        if (joinGroupResponsePacket.getSuccess()) {
            System.out.println("加入群组["+joinGroupResponsePacket.getGroupId()+"]成功!!!");
        } else {
            System.out.println("加入群组["+joinGroupResponsePacket.getGroupId()+"]失败, 请联系管理员!!!");
        }

    }
}
