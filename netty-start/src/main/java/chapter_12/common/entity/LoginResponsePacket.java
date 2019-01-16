package chapter_12.common.entity;

import chapter_12.common.Interface.Command;
import chapter_12.common.Interface.Packet;
import lombok.Data;

/**
 * @author jimmy
 * @create 2019-01-14 11:31
 * @desc 登录相应类
 **/
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
