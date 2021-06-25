package com.oauth2.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(SpringbootApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		log.info("\n----------------------------------------------------------\n\t"
				+ "平台应用启动成功! \n\t"
				+ "Local: \t\thttp://localhost:" + port + "\n\t"
				+ "External: \thttp://" + ip + ":" + port + "\n"
				+ "----------------------------------------------------------");
	}

}
