package pers.jimmy.chat.utils;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import pers.jimmy.chat.bean.Attributes;
import pers.jimmy.chat.bean.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jimmy
 * @create 2019-01-14 15:09
 * @desc 登录相关工具类
 **/
public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelGroupMap.get(groupId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup group) {
        groupIdChannelGroupMap.put(groupId, group);
    }

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUsername(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUsername());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static Channel getChannel(String username) {
        return userIdChannelMap.get(username);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

}
