package com.github.chasegnil.netty.client;

import com.github.chasegnil.netty.client.initializer.TelnetClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 简单的 telnet client.
 */
public final class TelnetClient {

    private static final boolean SSL = System.getProperty("ssl") != null;
    public static final String HOST = System.getProperty("host", "127.0.0.1");
    public static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8992" : "8023"));

    public static void main(String[] args) throws Exception {
        // 配置SSL
        SslContext sslContext;
        if (SSL) {
            sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslContext = null;
        }

        // 配置客户端
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new TelnetClientInitializer(sslContext));

            // 开始尝试连接
            Channel channel = b.connect(HOST, PORT).sync().channel();

            // 从stdin中读取命令
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (;;){
                String line = in.readLine();
                if (line == null) {
                    break;
                }

                // 发送接收到的数据到server
                lastWriteFuture = channel.writeAndFlush(line +"\r\n");

                // 如果用户输入“bye”命令，请等待服务器关闭连接先
                if ("bye".equals(line.toLowerCase())) {
                    channel.closeFuture().sync();
                    break;
                }

                // 在关闭通道之前，等待所有消息都被刷新
                if (lastWriteFuture != null) {
                    lastWriteFuture.sync();
                }
            }
        } finally {
            // 关闭所有事件循环以终止所有线程
            group.shutdownGracefully();
        }
    }
}
