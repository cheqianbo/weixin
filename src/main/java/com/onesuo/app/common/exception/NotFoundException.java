package com.onesuo.app.common.exception;

import com.onesuo.app.common.data.BaseErrorCode;

public class NotFoundException extends BaseException {
	public NotFoundException() {
		super(BaseErrorCode.BAD_NOT_FOUND, BaseErrorCode.BAD_NOT_FOUND_MSG);
	}

	public NotFoundException(String msg) {
		super(BaseErrorCode.BAD_NOT_FOUND, msg);
	}

	public NotFoundException(Integer code, String msg) {
		super(code, msg);
	}
}