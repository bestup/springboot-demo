package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class HardWareApplication {

    private static Logger log = LoggerFactory.getLogger(HardWareApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(HardWareApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t"
                + "springboot-demo is running! Access URLs:\n\t"
                + "Local: \t\thttp://localhost:" + port + "\n\t"
                + "External: \thttp://" + ip + ":" + port + "\n"
                + "----------------------------------------------------------");
    }

}
