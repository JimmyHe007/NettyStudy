package chapter_17.common.entity;

import chapter_17.common.Interface.Command;
import chapter_17.common.Interface.Packet;
import lombok.Data;

import java.util.List;

/**
 * @author jimmy
 * @create 2019-01-18 14:30
 * @desc 创建群聊请求包
 **/
@Data
public class CreateGroupRequestPacket extends Packet {
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
