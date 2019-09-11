package com.hsia.weblog.websocket;

import com.hsia.weblog.entity.User;
import com.hsia.weblog.exception.LogException;
import com.hsia.weblog.websocket.cache.ConnectionCache;
import com.hsia.weblog.websocket.thread.TailLogThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author : hsiachyikwok
 * @version : 1.0
 * @class_name : Tailog
 * @description : weblog
 * @create_date : 2019/8/28
 * @create_time : 14:25
 */
@Slf4j
//@Component
@ServerEndpoint(value = "/tailog/log")
public class TaiLogWS {

    private Process process;

    private InputStream inputStream;

    private static final String TAIL_CMD = "tail -1000f ";

    @OnOpen
    public void onOpen(Session session) throws IOException {
        // TODO 通过JWT 携带认证和用户信息
        User user = new User();
        user.setId(1);
        log.info("********open a new websocket connection********");
        ConnectionCache.getInstance().setCurrentOptLog(user.getId(), "cmd.exe ip config");
        String currentOptLog = ConnectionCache.getInstance().getCurrentOptLog(user.getId());
        ConnectionCache.getInstance().setUserInfo(session.getId(), user);
        if (currentOptLog == null) {
            throw LogException.LOG_FILE_NOT_SELECTED;
        }
        log.info("current operate log file:{} ", currentOptLog);
        try {
            // 执行tail -f命令
//            process = Runtime.getRuntime().exec(TAIL_CMD + currentOptLog);
            process = Runtime.getRuntime().exec(currentOptLog);
            inputStream = process.getInputStream();
            // 启动新的线程，防止InputStream阻塞处理WebSocket的线程
            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @OnClose
    public void onClose(CloseReason reason, Session session) {
        log.info("********close websocket connection !********");
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (process != null) {
            process.destroy();
        }
        resetCache(session);
        log.info("********close websocket connection complete !********");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("recive message from client:{}", message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("********websocket connection error !********");
        resetCache(session);
        throwable.printStackTrace();
    }

    /**
     * 重置当前用户缓存
     *
     * @param session
     */
    private void resetCache(Session session) {
        log.info("********reset current user cache*********");
        Integer userId = ConnectionCache.getInstance().getUserInfo(session.getId()).getId();
        ConnectionCache.getInstance().setCurrentOptLog(userId, null);
        // 清除用户信息
        ConnectionCache.getInstance().setUserInfo(session.getId(), null);
    }

}
