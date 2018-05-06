package com.github.chasegnil.netty.server;

import com.github.chasegnil.netty.server.initializer.TelnetServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class TelnetServer {

    private static final boolean SSL = System.getProperty("ssl") != null;
    private static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8992" : "8023"));

    public static void main(String[] args) throws Exception {
        // 配置SSL
        SslContext sslContext;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslContext = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslContext = null;
        }

        // 配置Server
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new TelnetServerInitializer(sslContext));

            // 启动服务，等待服务器套接字关闭
            b.bind(PORT).sync().channel().closeFuture().sync();
        } finally {
            // 关闭所有事件循环以终止所有线程
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
