package com.github.chasegnil.netty.client.initializer;

import com.github.chasegnil.netty.client.TelnetClient;
import com.github.chasegnil.netty.client.handler.TelnetClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

public class TelnetClientInitializer extends ChannelInitializer<SocketChannel> {

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    private static final TelnetClientHandler CLIENT_HANDLER = new TelnetClientHandler();

    private SslContext sslContext;

    public TelnetClientInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslContext != null) {
            pipeline.addLast(sslContext.newHandler(ch.alloc(), TelnetClient.HOST, TelnetClient.PORT));
        }

        // 添加文本行编解码器组合
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);

        pipeline.addLast(new LoggingHandler("client端动作", LogLevel.INFO));
        // 然后是业务逻辑
        pipeline.addLast(CLIENT_HANDLER);

    }
}
