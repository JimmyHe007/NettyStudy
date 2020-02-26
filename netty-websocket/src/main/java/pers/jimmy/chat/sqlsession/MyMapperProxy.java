package pers.jimmy.chat.sqlsession;

import pers.jimmy.chat.bean.Function;
import pers.jimmy.chat.bean.MapperBean;
import pers.jimmy.chat.config.MyConfiguration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class MyMapperProxy implements InvocationHandler {

    private MyConfiguration myConfiguration;
    private MySqlsession mySqlsession;

    public MyMapperProxy(MyConfiguration myConfiguration, MySqlsession mySqlsession) {
        this.myConfiguration = myConfiguration;
        this.mySqlsession = mySqlsession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean read = myConfiguration.readMapper("UserMapper.xml");
        if (!method.getDeclaringClass().getName().equals(read.getInterfaceName())) {
            return null;
        }
        List<Function> list = read.getList();
        if (list != null || list.size() > 0) {
            for (Function function : list) {
                if (method.getName().equals(function.getFuncname())) {
                    return mySqlsession.selectOne(function.getSql(), String.valueOf(args[0]));
                }
            }
        }
        return null;
    }

}
