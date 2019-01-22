package chapter_17.common.entity;

import lombok.Data;

/**
 * @author jimmy
 * @create 2019-01-17 14:37
 * @desc 用户标识
 **/
@Data
public class Session {

    private String userId;
    private String username;

    public Session(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
