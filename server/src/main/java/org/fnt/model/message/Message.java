package org.fnt.model.message;

import java.io.Serializable;
import java.util.List;

public class Message<T> implements Serializable {

    private MessageType type;
    private String userId;
    private String text;
    private List<T> body;

    public Message() {
    }

    public Message(MessageType type, String userId, String action, List<T> body) {
        this.type = type;
        this.userId = userId;
        this.text = action;
        this.body = body;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<T> getBody() {
        return body;
    }

    public void setBody(List<T> body) {
        this.body = body;
    }
}