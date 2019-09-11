package com.hsia.weblog.websocket.thread;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author hsia
 */
public class LogThread extends Thread{
    private BufferedReader reader;

    private SimpMessagingTemplate messagingTemplate;

    private String destination;

    public LogThread(InputStream in, SimpMessagingTemplate messagingTemplate,String destination) {
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.messagingTemplate = messagingTemplate;
        this.destination = destination;
    }

    @Override
    public void run() {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                messagingTemplate.convertAndSend(destination, line + "<br>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
