package com.onesuo.app.common.base;

import java.io.Serializable;

public class ResultDTO<T> implements Serializable {
    String message;
    int error;
    T data;

    public ResultDTO() {
    }

    public static <T> ResultDTO<T> wrapSuccess(T data) {
        ResultDTO<T> result = new ResultDTO();
        result.data = data;
        result.error = 0;
        result.message = "成功";
        return result;
    }

    public static <T> ResultDTO<T> wrapSuccess() {
        return wrapSuccess(null);
    }

    public static <T> ResultDTO<T> wrapError(int code, String msg) {
        ResultDTO<T> result = new ResultDTO();
        result.error = code;
        result.message = msg;
        return result;
    }

    public String getMessage() {
        return this.message;
    }

    public int getError() {
        return this.error;
    }
    public T getData() {
        return this.data;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setError(int error) {
        this.error = error;
    }

    public void setData(T data) {
        this.data = data;
    }
    public boolean isSuccess() {
        return this.error == 0;
    }
}
