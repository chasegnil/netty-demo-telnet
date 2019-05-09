package com.github.chasegnil;

import com.github.chasegnil.netty.server.TelnetServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private TelnetServer telnetServer;

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("应用启动成功！");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("开始执行实现CommandLineRunner接口的run方法");
        telnetServer.start();
        logger.info("完成执行实现CommandLineRunner接口的run方法");
    }
}
