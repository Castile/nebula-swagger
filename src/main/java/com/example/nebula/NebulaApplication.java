package com.example.nebula;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Slf4j
public class NebulaApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NebulaApplication.class);
        ConfigurableApplicationContext application = app.run(args);
        Environment env = application.getEnvironment();
        final String contextPath = env.getProperty("server.servlet.context-path");
        log.info("\r\n----------------------------------------------------------" +
                        "\r\nApplication is running! Access URLs:" +
                        "\r\ntDoc: http://{}:{}{}/doc.html " +
                        "\r\n----------------------------------------------------------",
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                contextPath, InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                contextPath);
    }

}
