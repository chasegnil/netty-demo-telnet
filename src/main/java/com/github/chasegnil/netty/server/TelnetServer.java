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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TelnetServer {

    private final Logger logger = LoggerFactory.getLogger(TelnetServer.class);

    private final boolean SSL = System.getProperty("ssl") != null;
    private final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8992" : "8023"));

    public void start() throws Exception {
        logger.debug("start {} server", "telnet");
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
