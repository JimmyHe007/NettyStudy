package chapter_20.common.entity;

import chapter_20.common.Interface.Command;
import chapter_20.common.Interface.Packet;

/**
 * @author jimmy
 * @create 2019-02-26 10:23
 * @desc 心跳请求包
 **/
public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
