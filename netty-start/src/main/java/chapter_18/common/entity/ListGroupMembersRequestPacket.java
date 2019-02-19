package chapter_18.common.entity;

import chapter_18.common.Interface.Command;
import chapter_18.common.Interface.Packet;
import lombok.Data;

/**
 * @author jimmy
 * @create 2019-02-18 10:33
 * @desc 展示群组成员请求包
 **/
@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
