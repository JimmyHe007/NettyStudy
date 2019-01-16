package chapter_1_5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author jimmy
 * @create 2019-01-09 17:06
 * @desc 服务端
 **/
public class NettyServer {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //接收连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        //读取数据
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
            .group(boss, worker)
            //指定IO模型
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                    nioSocketChannel.pipeline().addLast(new StringDecoder());
                    nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                            System.out.println(s);
                        }
                    });
                }
            });
            /*.bind(8000).addListener(new GenericFutureListener<Future<? super Void>>() {
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("端口绑定成功!");
                    } else {
                        System.out.println("端口绑定失败!");
                    }
                }
            });*/
        //绑定端口, 失败时端口号自动加1并重新绑定
        bind(serverBootstrap, 8000);
        serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
            protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                System.out.println("服务端启动中");
            }
        });
        //添加自定义属性, key-value
        serverBootstrap.attr(AttributeKey.newInstance("serverName"), "nettyServer");

        serverBootstrap.childAttr(AttributeKey.newInstance("clientKey"), "clientValue");

        //TCP心跳机制
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        //高实时性时使用的Nagle算法
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);

        //系统临时存放已完成三次握手的请求队列的最大长度, 连接频繁而服务器处理创建较慢时, 可适当调大
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);

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
