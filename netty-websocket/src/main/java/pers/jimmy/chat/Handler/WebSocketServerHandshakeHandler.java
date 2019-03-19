package pers.jimmy.chat.Handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.internal.StringUtil;
import pers.jimmy.chat.bean.Session;
import pers.jimmy.chat.utils.SessionUtil;

import java.util.UUID;

/**
 * @author jimmy
 * @create 2019-03-07 10:28
 * @desc 自定义握手处理器
 **/
public class WebSocketServerHandshakeHandler extends ChannelInboundHandlerAdapter {

    private final String websocketPath;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadSize;
    private final boolean allowMaskMismatch;

    WebSocketServerHandshakeHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch) {
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        this.maxFramePayloadSize = maxFrameSize;
        this.allowMaskMismatch = allowMaskMismatch;
    }

    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());
        if (msg instanceof WebSocketFrame) {
            if (session == null) {
                WebSocketServerHandler.forbiddenHttpRequestResponder();
            }
            ctx.fireChannelRead(msg);
        } else if (msg instanceof FullHttpRequest){
            final FullHttpRequest req = (FullHttpRequest) msg;
            try {
                String username = req.getUri().substring(req.getUri().indexOf("=")+1);
                if (req.method() == HttpMethod.GET && !(req.getUri().indexOf("=") < 0) && !StringUtil.isNullOrEmpty(username)) {
                    session = new Session(UUID.randomUUID().toString(), username);
                    SessionUtil.bindSession(session, ctx.channel());
                    WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(ctx.pipeline(), req, this.websocketPath), this.subprotocols, this.allowExtensions, this.maxFramePayloadSize, this.allowMaskMismatch);
                    final WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
                    if (handshaker == null) {
                        WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
                    } else {
                        ChannelFuture handshakeFuture = handshaker.handshake(ctx.channel(), req);
                        handshakeFuture.addListener(new ChannelFutureListener() {
                            public void operationComplete(ChannelFuture future) throws Exception {
                                if (!future.isSuccess()) {
                                    ctx.fireExceptionCaught(future.cause());
                                } else {
                                    ctx.fireUserEventTriggered(WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
                                    ChannelHandlerContext channelHandlerContext = ctx.fireUserEventTriggered(new WebSocketServerHandler.HandshakeComplete(req.uri(), req.headers(), handshaker.selectedSubprotocol()));
                                }

                            }
                        });
                        WebSocketServerHandler.setHandshaker(ctx.channel(), handshaker);
                        ctx.pipeline().replace(this, "WS403Responder", WebSocketServerHandler.forbiddenHttpRequestResponder());
                    }
                    return;
                }
                sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
            } finally {
                req.release();
            }

        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }

    }

    private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
        String protocol = "ws";
        if (cp.get(SslHandler.class) != null) {
            protocol = "wss";
        }

        return protocol + "://" + req.headers().get(HttpHeaderNames.HOST) + path;
    }
}
