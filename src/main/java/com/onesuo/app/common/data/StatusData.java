package com.onesuo.app.common.data;

public class StatusData {
	private int code;
	private String message;

	public StatusData() {
	}

	public StatusData(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}