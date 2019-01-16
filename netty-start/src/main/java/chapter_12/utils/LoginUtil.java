package chapter_12.utils;

import chapter_12.common.Interface.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author jimmy
 * @create 2019-01-14 15:09
 * @desc 登录相关工具类
 **/
public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }

}
