package com.example.dell.friendlychat;



// This Class is The Message structure
public class FriendlyMessage {
    String text;
    String uid;
    String toUid;
    public FriendlyMessage(){}

    public FriendlyMessage(String text, String uid, String toUid) {
        this.text = text;
        this.uid = uid;
        this.toUid = toUid;

    }

    public String getText() {
        return text;
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
