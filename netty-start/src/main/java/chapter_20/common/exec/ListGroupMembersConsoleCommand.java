package chapter_20.common.exec;

import chapter_20.common.Interface.ConsoleCommand;
import chapter_20.common.entity.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author jimmy
 * @create 2019-02-18 10:32
 * @desc 展示群组成员指令
 **/
public class ListGroupMembersConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {

        ListGroupMembersRequestPacket packet = new ListGroupMembersRequestPacket();
        System.out.println("请输入查询的群号: ");
        String groupId = scanner.next();
        packet.setGroupId(groupId);
        channel.writeAndFlush(packet);

    }
}
