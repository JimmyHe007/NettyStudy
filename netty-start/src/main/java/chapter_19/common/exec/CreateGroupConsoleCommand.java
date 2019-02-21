package chapter_19.common.exec;

import chapter_19.common.Interface.ConsoleCommand;
import chapter_19.common.entity.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author jimmy
 * @create 2019-01-18 14:26
 * @desc 创建群聊组指令
 **/
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {

        CreateGroupRequestPacket packet = new CreateGroupRequestPacket();
        System.out.println("[拉人群聊]输入userId列表, userId之间英文逗号隔开: ");
        String userIds = scanner.next();
        packet.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(packet);

    }

}
