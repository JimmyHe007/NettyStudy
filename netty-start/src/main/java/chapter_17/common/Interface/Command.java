package chapter_17.common.Interface;

/**
 * @author jimmy
 * @create 2019-01-14 10:14
 * @desc 指令
 **/
public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;

    Byte CREATE_GROUP_REQUEST = 5;

    Byte CREATE_GROUP_RESPONSE = 6;

}
