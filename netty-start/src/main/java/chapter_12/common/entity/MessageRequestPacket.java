package chapter_12.common.entity;

import chapter_12.common.Interface.Command;
import chapter_12.common.Interface.Packet;
import lombok.Data;

/**
 * @author jimmy
 * @create 2019-01-14 15:04
 * @desc 客户端发送至服务端的信息对象
 **/
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }

}
