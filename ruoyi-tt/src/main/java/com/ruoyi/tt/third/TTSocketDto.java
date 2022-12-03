package com.ruoyi.tt.third;

import lombok.Data;

@Data
public class TTSocketDto<T> {
    private static final long serialVersionUID = 1L;
    /** 成功 */
    public static final int SUCCESS = 0;
    /** 失败 */
    public static final int FAIL = 500;
    private String action;
    private UserInfo userInfo;
    private String packageName;
    private String androidId;
    private String channelId;
    private String messageId;
    private String tcpVersion;
    private T data;


    private int code;
    private String msg;
    private long tcpTime;

    public TTSocketDto<T> ok() {
        return restResult(null, SUCCESS, "操作成功");
    }

    public TTSocketDto<T> ok(T data)
    {
        return restResult(data, SUCCESS, "操作成功");
    }

    public TTSocketDto<T> ok(T data, String msg)    {
        return restResult(data, SUCCESS, msg);
    }

    public TTSocketDto<T> ok(T data,int code, String msg)    {
        return restResult(data, code, msg);
    }

    public TTSocketDto<T> fail()
    {
        return restResult(null, FAIL, "操作失败");
    }

    public TTSocketDto<T> fail(String msg)
    {
        return restResult(null, FAIL, msg);
    }

    public TTSocketDto<T> fail(T data) {
        return this.restResult(data, FAIL, "操作失败");
    }

    public TTSocketDto<T> fail(T data, String msg)
    {
        return restResult(data, FAIL, msg);
    }

    public  TTSocketDto<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private TTSocketDto<T> restResult(T data, int code, String msg){
        this.setTcpTime(System.currentTimeMillis());
        this.setCode(code);
        this.setData(data);
        this.setMsg(msg);
        return this;
    }

    public <T> Boolean isError()   {
        return !isSuccess();
    }

    public <T> Boolean isSuccess()  {
        return TTSocketDto.SUCCESS == this.getCode();
    }
}
