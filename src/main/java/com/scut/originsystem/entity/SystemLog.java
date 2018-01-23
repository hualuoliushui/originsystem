package com.scut.originsystem.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class SystemLog {
    private int id;
    private String role;
    private String log_time;
    private String username;
    private String log_message;
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SystemLog{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", log_time='" + log_time + '\'' +
                ", username='" + username + '\'' +
                ", log_message='" + log_message + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLog_time() {
        return log_time;
    }

    public void setLog_time(String log_time) {
        this.log_time = log_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLog_message() {
        return log_message;
    }

    public void setLog_message(String log_message) {
        this.log_message = log_message;
    }
}
