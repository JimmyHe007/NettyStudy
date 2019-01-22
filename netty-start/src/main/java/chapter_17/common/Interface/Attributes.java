package chapter_17.common.Interface;

import chapter_17.common.entity.Session;
import io.netty.util.AttributeKey;

/**
 * @author jimmy
 * @create 2019-01-14 15:06
 * @desc 属性
 **/
public interface Attributes {

    AttributeKey<Session> SESSION =AttributeKey.newInstance("session");

}
