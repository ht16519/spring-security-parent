package com.xh.demo.commons.enums;

import com.xh.demo.domain.vo.CommomMessage;

/**
 * @Name MessageEnum
 * @Description 返回信息枚举类
 */
public enum  MessageEnum implements CommomMessage {

    //基本错误
    SUCCESS(200, "操作成功"),
    USER_UNAUTHORIZED(403, "权限不够，请联系管理员！"),
    SYSTEM_PAGE_IS_NOT_EXIST(404, "抱歉, 您访问的页面不存在!"),
    FAIL(410, "操作失败"),
    REQUEST_LIMITED(500, "服务器繁忙，请稍后再试"),

    //100开头定义请求异常类型
    UNKNOWN_ERROR(10001, "未知错误"),
    CONTENT_TYPE_NOT_SUPPORTED(10002, "不支持内容类型"),
    REQUEST_METHOD_NOT_SUPPORTED(10003, "不支持的请求类型"),
    REQUEST_URL_NOT_SUPPORTED(10003, "不支持的请求地址"),
    PARAMETER_VERIFICATION_ERROR(10020, "参数不合法"),
    SYSTEM_SECURITY_CODE_ERROR(10021, "验证码已过期，请刷新再试"),
    INPUT_SECURITY_CODE_ERROR(10022, "验证码不正确"),
    PARAMETER_VERIFICATION_ERROR_ID(10023, "参数不合法，id不能为空"),
    INVALID_USER_INFO(10024, "无效的用户会话，请重新登录"),



    //TODO 在这里追加定义更多返回信息

    ;

    private int code;

    private String msg;

    MessageEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

}
