package pers.jimmy.chat.bean;

import lombok.Data;

@Data
public class Function {

    private String sqltype;
    private String funcname;
    private String sql;
    private Object resultType;
    private String parameterType;

}
