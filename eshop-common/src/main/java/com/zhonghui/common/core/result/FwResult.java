//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhonghui.common.core.result;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class FwResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int status;
    private String message;
    boolean success;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date time;
    private T data;

    public static <T> FwResult<T> ok() {
        return restResult(null, 200, true, "操作成功");
    }

    public static <T> FwResult<T> okMsg(String msg) {
        return restResult(null, 200, true, msg);
    }

    public static <T> FwResult<T> ok(T data) {
        return restResult(data, 200, true, "操作成功");
    }
    public static <T> FwResult<T> ok(T data,Integer code) {
        return restResult(data, code, true, "操作成功");
    }
    public static <T> FwResult<T> ok(T data, String msg) {
        return restResult(data, 200, true, msg);
    }

    public static <T> FwResult<T> okMeta(T data) {
        return restResult(data, 200, true, (String)null);
    }

    public static <T> FwResult<T> ok(T data, String msg, Object meta) {
        return restResult(data, 200, true, msg);
    }

    public static <T> FwResult<T> failed() {
        return restResult(null, 500, false, "操作失败");
    }

    public static <T> FwResult<T> failedMsg(String msg) {
        return restResult(null, 500, false, msg);
    }

    public static <T> FwResult<T> failedMsg(int code, String msg) {
        return restResult(null, code, false, msg);
    }

    public static <T> FwResult<T> tokenFailedMsg(String msg) {
        return restResult(null, 401, false, msg);
    }

    public static <T> FwResult<T> urlNotFoundMsg(String msg) {
        return restResult(null, 404, false, msg);
    }

    public static <T> FwResult<T> failed(T data) {
        return restResult(data, 500, false, "操作失败");
    }

    public static <T> FwResult<T> failed(T data, String msg) {
        return restResult(data, 500, false, msg);
    }

    private static <T> FwResult<T> restResult(T data, int code, boolean success, String msg) {
        FwResult fwResult = new FwResult();
        fwResult.setStatus(code);
        fwResult.setSuccess(success);
        fwResult.setData(data);
        fwResult.setMessage(msg);
        fwResult.setTime(new Date());
        return fwResult;
    }

    public FwResult() {
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Date getTime() {
        return this.time;
    }

    public T getData() {
        return this.data;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public void setTime(final Date time) {
        this.time = time;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FwResult)) {
            return false;
        } else {
            FwResult<?> other = (FwResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getStatus() != other.getStatus()) {
                return false;
            } else {
                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                if (this.isSuccess() != other.isSuccess()) {
                    return false;
                } else {
                    Object this$time = this.getTime();
                    Object other$time = other.getTime();
                    if (this$time == null) {
                        if (other$time != null) {
                            return false;
                        }
                    } else if (!this$time.equals(other$time)) {
                        return false;
                    }

                    Object this$data = this.getData();
                    Object other$data = other.getData();
                    if (this$data == null) {
                        if (other$data != null) {
                            return false;
                        }
                    } else if (!this$data.equals(other$data)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FwResult;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getStatus();
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        result = result * 59 + (this.isSuccess() ? 79 : 97);
        Object $time = this.getTime();
        result = result * 59 + ($time == null ? 43 : $time.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "FwResult(status=" + this.getStatus() + ", message=" + this.getMessage() + ", success=" + this.isSuccess() + ", time=" + this.getTime() + ", data=" + this.getData() + ")";
    }
}
