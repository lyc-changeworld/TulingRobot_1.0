package com.example.achuan.tulingrobot.bean;

/**
 * Created by achuan on 16-2-1.
 * 功能：映射服务器返回的结果
 */
public class Result
{
    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
