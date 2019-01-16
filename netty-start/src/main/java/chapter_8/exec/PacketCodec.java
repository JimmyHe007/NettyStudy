package chapter_8.exec;

import chapter_8.Interface.Command;
import chapter_8.Interface.Packet;
import chapter_8.entity.LoginRequestPacket;
import chapter_8.serializer.JSONSerializer;
import chapter_8.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jimmy
 * @create 2019-01-14 10:28
 * @desc 编码为二进制
 **/
public class PacketCodec {

    private static final int MAGIC_NUMBER = 0X12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap = new HashMap<Byte, Class<? extends Packet>>();
    private static final Map<Byte, Serializer> serializerMap = new HashMap<Byte, Serializer>();

    static {
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);

        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(Packet packet) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {

        //跳过magic number
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);

        //序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        //获取指令
        byte command = byteBuf.readByte();

        //数据包长度
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

}
