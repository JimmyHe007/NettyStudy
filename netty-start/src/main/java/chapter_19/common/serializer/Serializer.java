package chapter_19.common.serializer;

/**
 * @author jimmy
 * @create 2019-01-14 10:18
 * @desc 自定义序列化类
 **/
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
      * @author: jimmy
      * @create: 2019/1/14 10:19
      * @params:
      * @return: 使用的序列化算法标识
      * @desc:  序列化算法
      **/
    byte getSerializerAlgorithm();

    /**
      * @author: jimmy
      * @create: 2019/1/14 10:20
      * @params: 需要序列化的java对象
      * @return: 对象序列化后的二进制数据
      * @desc:  java对象转换为二进制
      **/
    byte[] serialize(Object object);

    /**
      * @author: jimmy
      * @create: 2019/1/14 10:21
      * @params: 二进制数据
      * @return: java对象
      * @desc:  二进制转为java对象
      **/
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
