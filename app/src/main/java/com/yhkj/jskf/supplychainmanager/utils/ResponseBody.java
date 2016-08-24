package com.yhkj.jskf.supplychainmanager.utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangxiaofei on 2016/5/6.
 */
public class ResponseBody<T> {
    @SerializedName("error")
    private int error = 65535;
    private T obj;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }


    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
