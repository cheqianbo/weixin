package com.onesuo.app.common.exception;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	protected Integer code;
	protected String msg;

	public BaseException() {
		this.code = 99999;
		this.msg = "未知错误";
	}

	public BaseException(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}