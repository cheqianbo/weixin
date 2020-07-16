package com.onesuo.app.common.exception;

import com.onesuo.app.common.data.BaseErrorCode;

public class ParamsException extends BaseException {
	private static final long serialVersionUID = 1L;

	public ParamsException() {
		super(BaseErrorCode.BAD_REQUEST_FORMAT, BaseErrorCode.BAD_REQUEST_FORMAT_MSG);
	}

	public ParamsException(String msg) {
		super(BaseErrorCode.BAD_REQUEST_FORMAT, msg);
	}

	public ParamsException(Integer code, String msg) {
		super(code, msg);
	}
}