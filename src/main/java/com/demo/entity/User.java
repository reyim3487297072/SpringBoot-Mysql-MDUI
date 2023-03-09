package com.demo.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class User {
    private int id;
    private String user;
    private String pass;
    private String name;
    private String head;
    private String ip;

    public User(int id, String user, String pass, String name, String head, String ip) {
        this.id = id;
        this.user = user;
        this.pass = pass;
        this.name = name;
        this.head = head;
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
