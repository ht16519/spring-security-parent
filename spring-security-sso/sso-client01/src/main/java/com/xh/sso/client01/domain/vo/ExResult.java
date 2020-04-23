package com.xh.sso.client01.domain.vo;

/**
 * @Name ExceptionResult
 * @Description 全局异常返回结果
 */
public class ExResult implements CommomMessage{

    private int code;

    private String msg;

    private long timestamp;

    private ExResult(CommomMessage commomMessage){
        this.code = commomMessage.getCode();
        this.msg = commomMessage.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public static ExResult build(CommomMessage commomMessage){
        return new ExResult(commomMessage);
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
