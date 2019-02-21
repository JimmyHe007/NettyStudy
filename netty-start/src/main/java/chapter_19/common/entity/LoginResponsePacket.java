package chapter_19.common.entity;

import chapter_19.common.Interface.Command;
import chapter_19.common.Interface.Packet;
import lombok.Data;

/**
 * @author jimmy
 * @create 2019-01-14 11:31
 * @desc 登录相应类
 **/
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String userId;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
