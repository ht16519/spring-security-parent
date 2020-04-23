package com.xh.sso.client01.domain.vo;


import com.xh.sso.client01.commons.enums.MessageEnum;

/**
 * @Name ApiResult
 * @Description API全局返回结果
 */
public class ApiResult<T> implements CommomMessage{

    private int code;

    private String msg;

    private T data;

    private long timestamp;

    private ApiResult(CommomMessage commomMessage, T data){
        this.code = commomMessage.getCode();
        this.msg = commomMessage.getMsg();
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public static <T> ApiResult<T>  success(){
        return build(MessageEnum.SUCCESS, null);
    }

    public static <T> ApiResult<T>  success(T data){
    return build(MessageEnum.SUCCESS, data);
    }

    public static <T> ApiResult<T>  failed(){
        return build(MessageEnum.FAIL, null);
    }

    public static <T> ApiResult<T>  failed(T data){
        return build(MessageEnum.FAIL, data);
    }

    public static <T> ApiResult<T>  res(int res){
        return res > 0 ? success() : failed();
    }

    public static <T> ApiResult<T>  build(CommomMessage commomMessage, T data){
        return new ApiResult<>(commomMessage, data);
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

    public T getData() {
        return data;
    }
}

