package pers.jimmy.chat.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import pers.jimmy.chat.entity.Session;
import pers.jimmy.chat.utils.SessionUtil;

import java.util.Locale;

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
            System.out.println("-------" + request);
            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("消息已收到!!!"));
        } else {
            String message = "unsupported frame type: " + webSocketFrame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
}
