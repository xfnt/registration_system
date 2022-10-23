package com.example.model.entity;

import java.time.LocalDateTime;

public class Timetable implements Sendable {
    private long id;
    private String employeeId;
    private String userId;
    private LocalDateTime time;

    public Timetable() {
    }

    public Timetable(long id, String employeeId, String userId, LocalDateTime time) {
        this.id = id;
        this.employeeId = employeeId;
        this.userId = userId;
        this.time = time;
    }

    public Timetable(String employeeId, LocalDateTime time) {
        this.employeeId = employeeId;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
