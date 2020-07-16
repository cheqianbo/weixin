package com.onesuo.app.common.exception;

import com.onesuo.app.common.data.BaseErrorCode;

public class ForbiddenException extends BaseException {
	public ForbiddenException() {
		super(BaseErrorCode.FORBIDDEN, BaseErrorCode.FORBIDDEN_MSG);
	}

	public ForbiddenException(Integer code, String msg) {
		super(code, msg);
	}
}