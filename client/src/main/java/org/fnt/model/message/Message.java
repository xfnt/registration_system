package org.fnt.model.message;

import java.io.Serializable;
import java.util.List;

public class Message<T> implements Serializable {

    private MessageType type;
    private String userId;
    private String text;
    private List<T> body;
    private int pageSize;
    private int pageNumber;

    public Message() {
    }

    public Message(MessageType type, String userId, String action, List<T> body) {
        this.type = type;
        this.userId = userId;
        this.text = action;
        this.body = body;
    }

    public Message(MessageType type, String userId, String action, List<T> body, int pageNumber, int pageSize) {
        this.type = type;
        this.userId = userId;
        this.text = action;
        this.body = body;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}