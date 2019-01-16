package chapter_15.client;

import chapter_15.client.handle.LoginResponseHandler;
import chapter_15.client.handle.MessageResponseHandler;
import chapter_15.common.entity.MessageRequestPacket;
import chapter_15.common.exec.PacketDecoder;
import chapter_15.common.exec.PacketEncoder;
import chapter_15.utils.LoginUtil;
import chapter_15.utils.Spliter;
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
                        //粘包
                        channel.pipeline().addLast(new Spliter());
                        channel.pipeline().addLast(new PacketDecoder());
                        channel.pipeline().addLast(new LoginResponseHandler());
                        channel.pipeline().addLast(new MessageResponseHandler());
                        channel.pipeline().addLast(new PacketEncoder());
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
        new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println("输入消息发送至服务端: ");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                MessageRequestPacket packet = new MessageRequestPacket();
                packet.setMessage(line);
                channel.writeAndFlush(packet);
            }
        }).start();
    }

}
