package chapter_19.client.handle;

import chapter_19.common.entity.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jimmy
 * @create 2019-02-18 17:04
 * @desc 退出群组响应包处理器
 **/
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {

        if (quitGroupResponsePacket.getSuccess()) {
            System.out.println("退出群组["+quitGroupResponsePacket.getGroupId()+"]成功!!!");
        } else {
            System.out.println("退出群组["+quitGroupResponsePacket.getGroupId()+"]失败, 请查找原因!");
        }

    }
}
