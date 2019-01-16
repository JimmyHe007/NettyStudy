package chapter_9_10.common.Interface;

import io.netty.util.AttributeKey;

/**
 * @author jimmy
 * @create 2019-01-14 15:06
 * @desc 属性
 **/
public interface Attributes {

    AttributeKey<Boolean> LOGIN =AttributeKey.newInstance("login");

}
