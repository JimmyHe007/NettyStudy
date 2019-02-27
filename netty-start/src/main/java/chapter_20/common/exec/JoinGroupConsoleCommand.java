package chapter_20.common.exec;

import chapter_20.common.Interface.ConsoleCommand;
import chapter_20.common.entity.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author jimmy
 * @create 2019-02-18 10:42
 * @desc 加入群组指令
 **/
public class JoinGroupConsoleCommand implements ConsoleCommand {


    @Override
    public void exec(Scanner scanner, Channel channel) {

        JoinGroupRequestPacket packet = new JoinGroupRequestPacket();
        System.out.println("请输入您想要加入的群组id:");
        String groupId = scanner.next();
        packet.setGroupId(groupId);
        channel.writeAndFlush(packet);

    }
}
