package com.onesuo.app.common.data;

/**
 * 错误码
 */
public class BaseErrorCode {

	public final static int OK = 200;
	public final static String OK_MSG = "";
	public final static int BAD_REQUEST_FORMAT = 400;
	public final static String BAD_REQUEST_FORMAT_MSG = "参数错误";
	public final static int ACCESS_DENIED = 401;
	public final static String ACCESS_DENIED_MSG = "访问被拒绝";
	public final static int FORBIDDEN = 403;
	public final static String FORBIDDEN_MSG = "没有权限";
	public final static int BAD_NOT_FOUND = 404;
	public final static String BAD_NOT_FOUND_MSG = "信息不存在";
	public final static int SERVER_ERROR = 500;
	public final static String SERVER_ERROR_MSG = "服务器异常";
	public final static int NOT_IMPLEMENTED_ERROR = 501;
	public final static String NOT_IMPLEMENTED_ERROR_MSG = "功能未实现";
	public final static int LOGIN_ERROR = 900001;
	public final static String LOGIN_ERROR_MSG = "账号或密码错误";
}