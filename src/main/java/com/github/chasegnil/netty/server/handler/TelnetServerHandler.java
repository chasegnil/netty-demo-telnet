package com.github.chasegnil.netty.server.handler;

import io.netty.channel.*;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理服务器端Channel
 */
@ChannelHandler.Sharable
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 为新连接发送问候
        ctx.write("欢迎来到：" + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("现在是：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r\n");
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        // 生成和编写响应
        String response;
        boolean close = false;
        if (request == null || "".equals(request)) {
            response = "请输入一些东西。\r\n";
        } else if ("bye".equals(request.toLowerCase())) {
            response = "祝你一天愉快！\r\n";
            close = true;
        } else {
            response = "你是不是说：" + request + "？\r\n";
        }

        // 不需要在这里写一个ChannelBuffer。
        // 在TelnetPipelineFactory插入的encoder将进行转换。
        ChannelFuture future = ctx.write(response);

        // '祝你一天愉快！'发送之后关闭连接
        // 即客户端发送的是'bye'
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
