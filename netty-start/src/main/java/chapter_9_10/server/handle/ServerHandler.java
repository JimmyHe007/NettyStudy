package chapter_9_10.server.handle;

import chapter_9_10.common.Interface.Packet;
import chapter_9_10.common.entity.LoginRequestPacket;
import chapter_9_10.common.entity.LoginResponsePacket;
import chapter_9_10.common.entity.MessageRequestPacket;
import chapter_9_10.common.entity.MessageResponsePacket;
import chapter_9_10.common.exec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author jimmy
 * @create 2019-01-14 11:20
 * @desc 服务端逻辑处理器
 **/
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginRequestPacket.setVersion(loginRequestPacket.getVersion());
            if (valid(loginRequestPacket)) {
                System.out.println(new Date() + ": 校验成功!");
                loginResponsePacket.setSuccess(true);
            } else {
                loginResponsePacket.setReason("账号密码校验失败!");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + ": 校验失败!");
            }
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date()+": 收到客户端消息: "+messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复: ["+messageRequestPacket.getMessage()+"]");
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    public boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
