package com.example.nebula.config;

import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.exception.AuthFailedException;
import com.vesoft.nebula.client.graph.exception.ClientServerIncompatibleException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.NotValidConnectionException;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * @author fulin
 * SessionPool
 */
@Slf4j
public class SessionPool {

    /**
     * 创建连接池
     *
     * @param maxCountSession 默认创建连接数
     * @param minCountSession 最大创建连接数
     * @param hostAndPort     机器端口列表
     * @param userName        用户名
     * @param passWord        密码
     * @throws UnknownHostException
     * @throws NotValidConnectionException
     * @throws IOErrorException
     * @throws AuthFailedException
     */
    public SessionPool(int maxCountSession, int minCountSession, String hostAndPort, String userName, String passWord) throws UnknownHostException, NotValidConnectionException, IOErrorException, AuthFailedException, ClientServerIncompatibleException {
        this.minCountSession = minCountSession;
        this.maxCountSession = maxCountSession;
        this.userName = userName;
        this.passWord = passWord;
        this.queue = new LinkedBlockingQueue<>(minCountSession);
        this.pool = this.initGraphClient(hostAndPort, maxCountSession, minCountSession);
        initSession();
    }

    public Session borrow() {
        Session se = queue.poll();
        if (se != null) {
            return se;
        }
        try {
            return this.pool.getSession(userName, passWord, true);
        } catch (Exception e) {
            log.error("execute borrow session fail, detail: ", e);
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void release() {
        Queue<Session> queue = this.queue;
        for (Session se : queue) {
            if (se != null) {
                boolean success = this.queue.offer(se);
                if (!success) {
                    se.release();
                }
            }
        }
    }

    public void close() {
        this.pool.close();
    }

    private void initSession() throws NotValidConnectionException, IOErrorException, AuthFailedException, ClientServerIncompatibleException {
        for (int i = 0; i < minCountSession; i++) {
            queue.offer(this.pool.getSession(userName, passWord, true));
        }
    }

    private NebulaPool initGraphClient(String hostAndPort, int maxConnSize, int minCount) throws UnknownHostException {
        List<HostAddress> hostAndPorts = getGraphHostPort(hostAndPort);
        NebulaPool pool = new NebulaPool();
        NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
        nebulaPoolConfig = nebulaPoolConfig.setMaxConnSize(maxConnSize);
        nebulaPoolConfig = nebulaPoolConfig.setMinConnSize(minCount);
        nebulaPoolConfig = nebulaPoolConfig.setIdleTime(1000 * 600);
        pool.init(hostAndPorts, nebulaPoolConfig);
        return pool;
    }

    private List<HostAddress> getGraphHostPort(String hostAndPort) {
        String[] split = hostAndPort.split(",");
        return Arrays.stream(split).map(item -> {
            String[] splitList = item.split(":");
            return new HostAddress(splitList[0], Integer.parseInt(splitList[1]));
        }).collect(Collectors.toList());
    }

    private Queue<Session> queue;

    private String userName;

    private String passWord;

    private int minCountSession;

    private int maxCountSession;

    private NebulaPool pool;

}
