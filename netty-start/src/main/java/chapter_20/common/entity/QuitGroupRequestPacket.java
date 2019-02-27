package chapter_20.common.entity;

import chapter_20.common.Interface.Command;
import chapter_20.common.Interface.Packet;
import lombok.Data;

/**
 * @author jimmy
 * @create 2019-02-18 16:57
 * @desc 退出群组请求包
 **/
@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_REQUEST;
    }
}
