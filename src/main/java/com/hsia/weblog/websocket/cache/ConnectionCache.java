package com.hsia.weblog.websocket.cache;

import com.hsia.weblog.entity.User;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hsia
 * 链接缓存
 */
public class ConnectionCache {
    /**
     * 当前操作的日志文件 userId--> log file
     */
    private static final ConcurrentHashMap<Integer, String> currentOptLog = new ConcurrentHashMap<>();
    /**
     * 用户链接信息
     */
    private static final ConcurrentHashMap<String, User> userConnectInfo = new ConcurrentHashMap<>();
    /**
     * 用户登录Session
     */
    private static final ConcurrentHashMap<String, User> userLoginSession = new ConcurrentHashMap<>();

    /**
     * 私有化构造器
     */
    private ConnectionCache() {

    }

    private static class ConnectionCacheHolder {
        private static final ConnectionCache instance = new ConnectionCache();
    }

    public static ConnectionCache getInstance() {
        return ConnectionCacheHolder.instance;
    }

    public String getCurrentOptLog(Integer userId) {
        return currentOptLog.get(userId);
    }

    public void setCurrentOptLog(Integer userId, String logfile) {
        currentOptLog.put(userId, logfile);
    }

    public User getUserInfo(String sessionId) {
        return userConnectInfo.get(sessionId);
    }

    public void setUserInfo(String sessionId, User user) {
        userConnectInfo.put(sessionId, user);
    }

    public void setUserLogin(String JSessionId, User user) {
        userLoginSession.put(JSessionId, user);
    }

    public User getUserInfoFromJSession(String JSessionId) {
        return userLoginSession.get(JSessionId);
    }
}
