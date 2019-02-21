package chapter_19.client.handle;

import chapter_19.common.entity.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jimmy
 * @create 2019-01-18 15:02
 * @desc 创建群聊响应处理器
 **/
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {
        System.out.print("群聊创建成功, id为["+createGroupResponsePacket.getGroupId()+"], 群里面有: "+createGroupResponsePacket.getUsernameList()+"\n");
    }

}
