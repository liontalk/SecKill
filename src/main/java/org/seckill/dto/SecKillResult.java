package org.seckill.dto;

/**
 * 封装json 结果
 * Created by ZhouZhe on 2018/6/6.
 */
public class SecKillResult<T> {


    private boolean success;

    private T Data;

    private String error;

    public SecKillResult(boolean success, T data) {
        this.success = success;
        Data = data;
    }

    public SecKillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "SecKillResult{" +
                "success=" + success +
                ", Data=" + Data +
                ", error='" + error + '\'' +
                '}';
    }
}
