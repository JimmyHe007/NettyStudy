package chapter_18.common.exec;

import chapter_18.common.Interface.ConsoleCommand;
import chapter_18.common.entity.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author jimmy
 * @create 2019-01-18 14:49
 * @desc 发送消息指令
 **/
public class MessageConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入对方用户id: ");
        String toUserId = scanner.next();
        System.out.println("输入信息: ");
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
