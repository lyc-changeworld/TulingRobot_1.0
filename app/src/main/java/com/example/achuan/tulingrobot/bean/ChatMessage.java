package com.example.achuan.tulingrobot.bean;

import java.util.Date;

/**
 * Created by achuan on 16-2-1.
 * 功能:对布局中相关信息进行排列
 */
public class ChatMessage {
    private String name;//名称
    private String msg;//消息内容
    private Type type;//类型（客户发送的or接收服务器的）
    private Date date;//日期

    public ChatMessage() {

    }
    /*枚举类型*/
    public  enum Type
    {
        INCOMING,OUTCOMING
    }

    public ChatMessage(String msg, Type type, Date date) {
        this.msg = msg;
        this.type = type;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}