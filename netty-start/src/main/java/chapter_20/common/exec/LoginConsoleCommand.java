package chapter_20.common.exec;

import chapter_20.common.Interface.ConsoleCommand;
import chapter_20.common.entity.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author jimmy
 * @create 2019-01-18 14:44
 * @desc 登录控制台指令
 **/
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        System.out.println("输入用户名进行登录: ");
        String username = scanner.nextLine();
        loginRequestPacket.setUsername(username);
        loginRequestPacket.setPassword("123");

        channel.writeAndFlush(loginRequestPacket);

        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
    }
}
