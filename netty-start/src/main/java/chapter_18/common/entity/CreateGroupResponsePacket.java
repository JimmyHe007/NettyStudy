package chapter_18.common.entity;

import chapter_18.common.Interface.Command;
import chapter_18.common.Interface.Packet;
import lombok.Data;

import java.util.List;

/**
 * @author jimmy
 * @create 2019-01-18 14:55
 * @desc 创建群聊响应包
 **/
@Data
public class CreateGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private List<String> usernameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
