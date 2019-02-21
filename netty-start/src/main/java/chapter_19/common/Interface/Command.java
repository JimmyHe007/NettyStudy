package chapter_19.common.Interface;

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

    Byte LIST_GROUP_MEMBERS_REQUEST = 7;

    Byte LIST_GROUP_MEMBERS_RESPONSE = 8;

    Byte JOIN_GROUP_REQUEST = 9;

    Byte JOIN_GROUP_RESPONSE = 10;

    Byte QUIT_GROUP_REQUEST = 11;

    Byte QUIT_GROUP_RESPONSE = 12;

    Byte MESSAGE_GROUP_REQUEST = 13;

    Byte MESSAGE_GROUP_RESPONSE = 14;

}
