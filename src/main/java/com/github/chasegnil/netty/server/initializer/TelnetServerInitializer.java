package com.github.chasegnil.netty.server.initializer;

import com.github.chasegnil.netty.server.handler.TelnetServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

/**
 * Creates a newly configured {@link io.netty.channel.ChannelPipeline} for a new channel.
 */
public class TelnetServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    private static final TelnetServerHandler SERVER_HANDLER = new TelnetServerHandler();

    private SslContext sslContext;

    public TelnetServerInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslContext != null) {
            pipeline.addLast(sslContext.newHandler(ch.alloc()));
        }

        // 首先添加文本行编解码器组合
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // 编码器和解码器是静态的,这些都是可共享的
        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);

        // 然后是业务逻辑
        pipeline.addLast(SERVER_HANDLER);
    }
}
