package com.base.frame.net;

/**
 * Created by liuchengran on 2017/8/18.
 */

public class ApiResponse<T> {
    private String message;      // 返回信息
    private int code = -1;    // 服务器返回码
    private T data;      	// 返回值对象
    private int datasize;

    public ApiResponse(int errorCode, String errorMsg) {
        this.code = errorCode;
        this.message = errorMsg;
    }

    public int getDatasize() {
        return datasize;
    }

    public void setDatasize(int datasize) {
        this.datasize = datasize;
    }

    public ApiResponse() {
        super();
    }

    public boolean isSuccess() {
        return code == 1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

}
