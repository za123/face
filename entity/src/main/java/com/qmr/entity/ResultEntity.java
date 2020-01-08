package com.qmr.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResultEntity {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    private String status;
    private String msg;
    @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
    private Object data;

    /**
     * 成功无数据
     * @param msg
     * @return
     */
    public static ResultEntity success(String msg) {
        return new ResultEntity(SUCCESS, msg, null);
    }

    public static ResultEntity success(String msg, Object data) {
        return new ResultEntity(SUCCESS, msg, data);
    }

    /**
     * 失败无数据
     * @param msg
     * @return
     */
    public static ResultEntity fail(String msg) {
        return new ResultEntity(FAIL, msg, null);
    }

}
