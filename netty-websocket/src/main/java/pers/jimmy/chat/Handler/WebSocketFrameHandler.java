package pers.jimmy.chat.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import pers.jimmy.chat.bean.Session;
import pers.jimmy.chat.utils.SessionUtil;

/**
 * @author jimmy
 * @create 2019-03-05 15:51
 * @desc
 **/
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame) throws Exception {
        Session session = SessionUtil.getSession(channelHandlerContext.channel());
        if (session == null) {
            WebSocketServerHandler.forbiddenHttpRequestResponder();
            channelHandlerContext.channel().close();
        }
        if (webSocketFrame instanceof TextWebSocketFrame) {
            String request = ((TextWebSocketFrame) webSocketFrame).text();
            JSONObject jsonObject = JSON.parseObject(request);
            String username = jsonObject.get("recv").toString();
            String message = jsonObject.get("message").toString();
            System.out.println("接收人: "+username+", 消息为:" + message);
            SessionUtil.getChannel(username).writeAndFlush(new TextWebSocketFrame(message));
            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("消息已收到!!!"));
        } else {
            String message = "unsupported frame type: " + webSocketFrame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
}
