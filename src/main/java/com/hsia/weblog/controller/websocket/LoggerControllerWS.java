package com.hsia.weblog.controller.websocket;

import com.hsia.weblog.api.ILogService;
import com.hsia.weblog.entity.Log;
import com.hsia.weblog.websocket.thread.LogThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.io.InputStream;
import java.security.Principal;

/**
 * @author hsia 向网页输出日志
 */
@Controller
@Slf4j
public class LoggerControllerWS {
    /**
     * Spring WebSocket消息发送模板
     */
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ILogService logService;

    private InputStream inputStream;

    private Process process;

    //发送广播通知
    //接收客户端发来的消息，客户端发送消息地址为：/app/addBroadcast
    @MessageMapping("/addBroadcast")
    //向客户端发送广播消息（方式一），客户端订阅消息地址为：/topic/broadcast
    @SendTo("/topic/broadcast")
    public boolean broadcast(String notice, Principal fromUser) {
        WsMessage msg = new WsMessage();
        msg.setFromName(fromUser.getName());
        msg.setContent(notice);
        log.info("websocket message:{}", msg);
        Log logInfo = logService.getLogInfoById(Integer.parseInt((String) msg.getContent()));
        log.info("logInfo:{}",logInfo);
        try {
//            String cmd = "tail -1000f " + logInfo.getDirName() + logInfo.getFileName();
            String cmd = logInfo.getFileName();
            process = Runtime.getRuntime().exec(cmd);
            inputStream = process.getInputStream();
            LogThread thread = new LogThread(inputStream, messagingTemplate, "/topic/broadcast");
            thread.start();
        } catch (Exception ex) {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
        //向客户端发送广播消息（方式二），客户端订阅消息地址为：/topic/broadcast
        //messagingTemplate.convertAndSend("/topic/broadcast", msg);
        return true;
    }

    //发送点对点消息
    //接收客户端发来的消息，客户端发送消息地址为：/app/msg
    //向当前发消息客户端（就是自己）发送消息的发送结果，客户端订阅消息地址为：/user/queue/msg/result
    @MessageMapping("/msg")
    @SendToUser("/queue/msg/result")
    public boolean sendMsg(WsMessage message, Principal fromUser) {
        message.setFromName(fromUser.getName());
        message.setToName("xiaqiguo");
        //向指定客户端发送消息，第一个参数Principal.name为前面websocket握手认证通过的用户name（全局唯一的），客户端订阅消息地址为：/user/queue/msg/new
        messagingTemplate.convertAndSendToUser(message.getToName(), "/queue/msg/new", message);
        return true;

    }
}
