package chapter_20.common.Interface;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author jimmy
 * @create 2019-01-18 14:19
 * @desc 控制台指令
 **/
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);

}
