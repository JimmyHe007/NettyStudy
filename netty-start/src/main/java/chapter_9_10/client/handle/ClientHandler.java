package chapter_9_10.client.handle;

import chapter_9_10.common.Interface.Packet;
import chapter_9_10.common.entity.LoginRequestPacket;
import chapter_9_10.common.entity.LoginResponsePacket;
import chapter_9_10.common.entity.MessageResponsePacket;
import chapter_9_10.common.exec.PacketCodec;
import chapter_9_10.utils.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author jimmy
 * @create 2019-01-14 11:20
 * @desc 客户端逻辑处理器
 **/
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date()+": 客户端开始登录");

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(123);
        packet.setUsername("zhangsan");
        packet.setPassword("password");

        ByteBuf byteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), packet);

        ctx.channel().writeAndFlush(byteBuf);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": 客户端登录成功!");
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                System.out.println(new Date() + ": 客户端登录失败!原因为: " + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date()+": 收到服务端的消息: "+messageResponsePacket.getMessage());
        }
    }
}
