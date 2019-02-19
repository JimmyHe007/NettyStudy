package chapter_18.common.entity;

import chapter_18.common.Interface.Command;
import chapter_18.common.Interface.Packet;
import lombok.Data;

/**
 * @author jimmy
 * @create 2019-02-18 16:58
 * @desc 退出群组响应包
 **/
@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private Boolean success;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
