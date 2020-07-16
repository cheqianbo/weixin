package com.onesuo.app.common.exception;

import com.onesuo.app.common.data.BaseErrorCode;

/**
 * 未实现异常
 */
public class NotImplementedException extends BaseException {
	public NotImplementedException() {
		super(BaseErrorCode.NOT_IMPLEMENTED_ERROR, BaseErrorCode.NOT_IMPLEMENTED_ERROR_MSG);
	}

	public NotImplementedException(String msg) {
		super(BaseErrorCode.NOT_IMPLEMENTED_ERROR, msg);
	}

	public NotImplementedException(Integer code, String msg) {
		super(code, msg);
	}
}