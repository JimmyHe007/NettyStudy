package chapter_20.common.entity;

import chapter_20.common.Interface.Command;
import chapter_20.common.Interface.Packet;
import lombok.Data;

/**
 * @author jimmy
 * @create 2019-02-18 10:39
 * @desc 加入群组请求包
 **/
@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
