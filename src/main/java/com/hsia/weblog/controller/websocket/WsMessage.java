package com.hsia.weblog.controller.websocket;

/**
 * @author hsia
 */
public class WsMessage {

    private String fromName;

    private String toName;

    private Object content;

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WsMessage{" +
                "fromName='" + fromName + '\'' +
                ", toName='" + toName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
