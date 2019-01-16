package chapter_8.entity;

import chapter_8.Interface.Command;
import chapter_8.Interface.Packet;
import lombok.Data;

/**
 * @author jimmy
 * @create 2019-01-14 10:16
 * @desc 登录请求包处理
 **/
@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;

    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
