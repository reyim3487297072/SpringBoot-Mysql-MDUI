package com.demo.util;

import lombok.Data;

@Data // 提供类所有属性的 getting 和 setting 方法
public class ResultJson<T> {
    Integer code;
    String msg;
    T data;

    /**
     * 自定义返回
     *
     * @param code 状态
     * @param msg  结果
     * @param data 主数据
     */
    public ResultJson(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 200成功返回并无data
     *
     * @param msg 结果
     */
    public ResultJson(String msg) {
        this.code = 200;
        this.msg = msg;
        this.data = null;
    }

    /**
     * 成功返回并有data
     *
     * @param msg  结果
     * @param data 主数据
     */
    public ResultJson(String msg, T data) {
        this.code = 200;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 自定义状态和结果
     *
     * @param code 状态
     * @param msg  结果
     */
    public ResultJson(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }
}
