package com.onesuo.app.common.exception;

import com.onesuo.app.common.data.ErrorCode;

public class CommonException extends BaseException {
	private static final long serialVersionUID = 1L;

	public CommonException() {
		super(ErrorCode.NO_SUCCESS_ERROR, ErrorCode.NO_SUCCESS_ERROR_MSG);
	}

	public CommonException(String msg) {
		super(ErrorCode.NO_SUCCESS_ERROR, msg);
	}

	public CommonException(Integer code, String msg) {
		super(code, msg);
	}
}