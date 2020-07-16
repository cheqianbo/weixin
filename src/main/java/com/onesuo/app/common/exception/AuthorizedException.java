package com.onesuo.app.common.exception;

import com.onesuo.app.common.data.BaseErrorCode;

public class AuthorizedException extends BaseException {
	private static final long serialVersionUID = 1L;

	public AuthorizedException() {
		super(BaseErrorCode.ACCESS_DENIED, BaseErrorCode.ACCESS_DENIED_MSG);
	}

	public AuthorizedException(Integer code, String msg) {
		super(code, msg);
	}

	public AuthorizedException(String msg) {
		super(BaseErrorCode.ACCESS_DENIED, msg);
	}
}