package chapter_17.server.handle;

import chapter_17.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author jimmy
 * @create 2019-01-16 11:08
 * @desc 登录验证处理器
 **/
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            //登录成功后移除当前处理器, 避免资源占用
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前登录连接验证完毕, 无需再次验证, AuthHandler被移除!");
        } else {
            System.out.println("无登录验证, 强制关闭连接!");
        }
    }
}
