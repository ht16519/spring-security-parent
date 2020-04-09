package com.xh.demo.commons.exception;

import com.xh.demo.domain.vo.CommomMessage;

/**
 * @Name BusinessException
 * @Description 业务异常类
 */
public class BusinessException extends RuntimeException implements CommomMessage {

	private int code;

	private String msg;

	private BusinessException(CommomMessage commomMessage){
		this.code = commomMessage.getCode();
		this.msg = commomMessage.getMsg();
	}

	public static BusinessException build(CommomMessage commomMessage){
		return new BusinessException(commomMessage);
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
