package chapter_8;

import chapter_8.Interface.Packet;
import chapter_8.entity.LoginRequestPacket;
import chapter_8.exec.PacketCodec;
import chapter_8.serializer.JSONSerializer;
import chapter_8.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author jimmy
 * @create 2019-01-14 11:10
 * @desc 测试编码解码
 **/
public class PacketCodecTest {

    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(123);
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodec packetCodeC = new PacketCodec();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }

}
