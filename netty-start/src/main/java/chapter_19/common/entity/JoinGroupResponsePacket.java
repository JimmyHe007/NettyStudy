package chapter_19.common.entity;

import chapter_19.common.Interface.Command;
import chapter_19.common.Interface.Packet;
import lombok.Data;

/**
 * @author jimmy
 * @create 2019-02-18 10:40
 * @desc 加入群组响应包
 **/
@Data
public class JoinGroupResponsePacket extends Packet {

    private Boolean success;

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
