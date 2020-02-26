package pers.jimmy.chat;

import pers.jimmy.chat.bean.User;
import pers.jimmy.chat.mapper.UserMapper;
import pers.jimmy.chat.sqlsession.MySqlsession;

public class MyORM {

    public static void main(String[] args) {
        MySqlsession sqlsession = new MySqlsession();
        UserMapper mapper = sqlsession.getMapper(UserMapper.class);
        User user = mapper.getUserById("1");
        System.out.println(user);
    }

}
