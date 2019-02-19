package chapter_18.client.handle;

import chapter_18.common.entity.LoginResponsePacket;
import chapter_18.common.entity.Session;
import chapter_18.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author jimmy
 * @create 2019-01-14 17:28
 * @desc 登录响应处理器
 **/
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            SessionUtil.bindSession(new Session(loginResponsePacket.getUserId(), null), channelHandlerContext.channel());
            System.out.println(new Date() + ": 客户端登录成功!id为: "+loginResponsePacket.getUserId());
        } else {
            System.out.println(new Date() + ": 客户端登录失败!原因为: " + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭!");
    }
}
