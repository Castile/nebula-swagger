package com.example.nebula.config;

import com.vesoft.nebula.client.graph.net.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Description 配置全局唯一session链接
 **/
@Component
public class NebulaSession {
    @Autowired
    NebulaGraphProperties nebulaGraphProperties;

    @Bean
    public Session session() throws Exception {
        SessionPool sessionPool = new SessionPool(nebulaGraphProperties.getMaxConnSize(), nebulaGraphProperties.getMinConnsSize(), nebulaGraphProperties.getHostAddresses().get(0),
                nebulaGraphProperties.getUserName(), nebulaGraphProperties.getPassword());
        return sessionPool.borrow();
    }

}
