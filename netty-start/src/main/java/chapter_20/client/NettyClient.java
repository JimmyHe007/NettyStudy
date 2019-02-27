package chapter_20.client;

import chapter_20.client.handle.*;
import chapter_20.common.exec.ConsoleCommandManager;
import chapter_20.common.exec.LoginConsoleCommand;
import chapter_20.common.exec.PacketDecoder;
import chapter_20.common.exec.PacketEncoder;
import chapter_20.common.handler.IMIdleStateHandler;
import chapter_20.utils.SessionUtil;
import chapter_20.utils.Spliter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author jimmy
 * @create 2019-01-09 17:10
 * @desc 客户端
 **/
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new IMIdleStateHandler());//心跳检测
                        //粘包
                        channel.pipeline().addLast(new Spliter());
                        channel.pipeline().addLast(new PacketDecoder());
                        channel.pipeline().addLast(new LoginResponseHandler());
                        channel.pipeline().addLast(new CreateGroupResponseHandler());
                        channel.pipeline().addLast(new ListGroupResponseHandler());
                        channel.pipeline().addLast(new JoinGroupResponseHandler());
                        channel.pipeline().addLast(new QuitGroupResponseHandler());
                        channel.pipeline().addLast(new MessageResponseHandler());
                        channel.pipeline().addLast(new GroupMessageResponseHandler());
                        channel.pipeline().addLast(new PacketEncoder());
                        channel.pipeline().addLast(new HeartBeatTimerHandler());
                    }
                });

        String host = "127.0.0.1";
        int port = 8000;

        bootstrap.attr(AttributeKey.newInstance("clientName"), "nettyClient");
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);
        connect(bootstrap, host, port, MAX_RETRY);

    }

    private static final int MAX_RETRY = 5;

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功, 启动控制台输入线程!");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重连次数过多, 放弃连接!");
            } else {
                int order = (MAX_RETRY - retry) +1;
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败, 第"+order+"次重连...");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }

}
