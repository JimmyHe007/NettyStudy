package pers.jimmy.chat.sqlsession;

import pers.jimmy.chat.config.MyConfiguration;

import java.lang.reflect.Proxy;

public class MySqlsession {

    private Excutor excutor = new MyExcutor();

    private MyConfiguration myConfiguration = new MyConfiguration();

    public <T> T selectOne(String statement, Object parameter) {
        return excutor.query(statement, parameter);
    }

    public <T> T getMapper(Class<T> tClass) {
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, new MyMapperProxy(myConfiguration, this));
    }

}
