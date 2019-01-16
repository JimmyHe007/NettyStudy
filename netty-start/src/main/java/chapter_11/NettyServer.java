package chapter_11;

import chapter_11.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author jimmy
 * @create 2019-01-14 15:44
 * @desc 服务端
 **/
public class NettyServer {

    public static void main(String[] args) {

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //接收连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        //读取数据
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap.group(boss, worker)
                //指定IO模型
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new InBoundHandlerA());
                        nioSocketChannel.pipeline().addLast(new InBoundHandlerB());
                        nioSocketChannel.pipeline().addLast(new InBoundHandlerC());

                        nioSocketChannel.pipeline().addLast(new ServerHandler());

                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerA());
                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerB());
                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerC());
                    }
        });
        serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
            protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                System.out.println("服务端启动中");
            }
        });
        bind(serverBootstrap, 8000);

    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口["+port+"]绑定成功!");
                } else {
                    System.out.println("端口["+port+"]绑定失败!");
                    bind(serverBootstrap, port+1);
                }
            }
        });
    }

}
