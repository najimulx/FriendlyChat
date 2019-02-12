package com.example.dell.friendlychat;

public class FriendlyUser {
    String uid;
    String name;

    public FriendlyUser() {
    }

    public FriendlyUser(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
