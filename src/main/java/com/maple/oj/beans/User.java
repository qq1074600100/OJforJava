package com.maple.oj.beans;

public class User {
    private Integer uid;
    private String username;
    private String password;
    private boolean isManager;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isManager = false;
    }

    public User(String username, String password, boolean isManager) {
        this.username = username;
        this.password = password;
        this.isManager = isManager;
    }

    public User(Integer uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.isManager = false;
    }

    public User(Integer uid, String username, String password, boolean isManager) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.isManager = isManager;
    }


    public Integer getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
