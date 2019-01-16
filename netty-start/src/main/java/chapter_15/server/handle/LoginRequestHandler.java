package chapter_15.server.handle;

import chapter_15.common.entity.LoginRequestPacket;
import chapter_15.common.entity.LoginResponsePacket;
import chapter_15.utils.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author jimmy
 * @create 2019-01-14 17:28
 * @desc 登录请求处理器
 **/
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginRequestPacket.setVersion(loginRequestPacket.getVersion());
        if (valid(loginRequestPacket)) {
            System.out.println(new Date() + ": 校验成功!");
            loginResponsePacket.setSuccess(true);
            //服务端标记登录状态
            LoginUtil.markAsLogin(channelHandlerContext.channel());
        } else {
            loginResponsePacket.setReason("账号密码校验失败!");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 校验失败!");
        }
        channelHandlerContext.channel().write(loginResponsePacket);
    }
    public boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
