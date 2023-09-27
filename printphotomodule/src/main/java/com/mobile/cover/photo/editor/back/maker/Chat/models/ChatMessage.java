package com.mobile.cover.photo.editor.back.maker.Chat.models;

import android.text.format.DateFormat;

import java.util.concurrent.TimeUnit;


public class ChatMessage {
    private String message;
    private String imageurl;
    private String timestamp;
    private Type type;
    private String sender;

    public ChatMessage(String message, String timestamp, Type type, String imageurl) {
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
        this.imageurl = imageurl;
    }

//    public ChatMessage(String message, long timestamp, Type type, String sender) {
//        this(message, timestamp, type);
//        this.sender = sender;
//    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


//    public String getFormattedTime() {
//
//        long oneDayInMillis = TimeUnit.DAYS.toMillis(1); // 24 * 60 * 60 * 1000;
//
//        long timeDifference = System.currentTimeMillis() - timestamp;
//
//        return timeDifference < oneDayInMillis
//                ? DateFormat.format("hh:mm a", timestamp).toString()
//                : DateFormat.format("dd MMM - hh:mm a", timestamp).toString();
//    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public enum Type {
        SENT, RECEIVED
    }
}
