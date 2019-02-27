package chapter_20.server.handle;

import chapter_20.common.entity.LoginRequestPacket;
import chapter_20.common.entity.LoginResponsePacket;
import chapter_20.common.entity.Session;
import chapter_20.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author jimmy
 * @create 2019-01-14 17:28
 * @desc 登录请求处理器
 **/
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {

        LoginResponsePacket packet = new LoginResponsePacket();
        String userId = UUID.randomUUID().toString();
        packet.setUserId(userId);
        SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), channelHandlerContext.channel());
        packet.setSuccess(true);

        channelHandlerContext.channel().writeAndFlush(packet);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unbindSession(ctx.channel());
    }
}
