package chapter_18.client.handle;

import chapter_18.common.entity.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jimmy
 * @create 2019-02-18 11:06
 * @desc 展示群组用户响应包处理器
 **/
public class ListGroupResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMembersResponsePacket listGroupMembersResponsePacket) throws Exception {

        System.out.println("您查询的id为["+listGroupMembersResponsePacket.getGroupId()+"]的群组成员列表如下: ");
        listGroupMembersResponsePacket.getUsers().forEach((String userId, String username)-> System.out.println("成员id:["+userId+"], "+"成员姓名:["+username+"]"));

    }
}
