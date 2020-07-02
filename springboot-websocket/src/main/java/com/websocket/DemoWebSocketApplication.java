package com.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @date 2020/7/2
 */
@SpringBootApplication
@Slf4j
public class DemoWebSocketApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(DemoWebSocketApplication.class, args);
        System.setProperty("spring.devtools.restart.enabled", "false");
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        //没有用到servlet.context-path，注释掉
        log.info("\n----------------------------------------------------------\n\t"
                + "(♥◠‿◠)ﾉﾞ  系统启动成功   ლ(´ڡ`ლ)\n"
                + "\tBootApi is running! Access URLs:\n\t" + "Local: \t\t\thttp://localhost:" + port + path
                + "/\n\t" + "External: \t\thttp://" + ip + ":" + port + path + "/\n\t" + "swagger-ui: \thttp://" + ip
                + ":" + port + path + "/swagger-ui.html\n\t" + "Doc: \t\t\thttp://" + ip + ":" + port + path
                + "/doc.html\n" + "----------------------------------------------------------");
    }
}

