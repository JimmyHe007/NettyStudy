package chapter_9_10.common.serializer;

import com.alibaba.fastjson.JSON;

/**
 * @author jimmy
 * @create 2019-01-14 10:24
 * @desc fastjson序列化实现
 **/
public class JSONSerializer implements Serializer {

    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }

}
