package pers.jimmy.chat.http;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import pers.jimmy.chat.Handler.WebSocketServerHandshakeHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author hx
 * @create 19-3-20 下午3:33
 * @desc
 **/
public class WebPageHandler {

    private static final String location;

    private static final String suffix;

    private static final File NOT_FOUND;

    static {
        location = "/home/hx/Documents/git/netty-study/netty-websocket/src/main/resources";
        suffix = ".html";
        NOT_FOUND = new File(location+"/error/404.html");
    }

    public HttpResponse sendResponse() {
        return null;
    }

    public HttpResponse sendData() {
        return null;
    }

    public static void sendResource(ChannelHandlerContext channelHandlerContext, FullHttpRequest request) throws IOException {
        String path = request.uri();
        if ("favicon.ico".equals(path)) {
            WebSocketServerHandshakeHandler.sendHttpResponse(channelHandlerContext, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
        }
        File resource = new File(location+path);
        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        if (!resource.exists()) {
            resource = NOT_FOUND;
            response.setStatus(HttpResponseStatus.NOT_FOUND);
        }
        RandomAccessFile file = new RandomAccessFile(resource, "r");
        if (path.endsWith(suffix)) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        } else if (path.endsWith(".js")) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/x-javascript");
        } else if (path.endsWith(".css")) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/css; charset=UTF-8");
        }
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        if (keepAlive) {
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, resource.length());
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        channelHandlerContext.write(response);
        if (channelHandlerContext.pipeline().get(SslHandler.class) == null) {
            channelHandlerContext.write(new DefaultFileRegion(file.getChannel(), 0, file.length()),
                    channelHandlerContext.newProgressivePromise());
        } else {
            channelHandlerContext.write(new ChunkedNioFile(file.getChannel()));
        }
        ChannelFuture future = channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
        file.close();
    }

}
