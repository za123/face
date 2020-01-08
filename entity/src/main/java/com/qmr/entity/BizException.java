package com.qmr.entity;

/**
 * 自定义异常类 捕获业务异常
 */
public class BizException extends RuntimeException {

    /**
     * json解析异常
     * @return
     */
    public ResultEntity notFound() {
        return ResultEntity.fail("啊哦,图片检测失败了,换一张图片吧!");
    }
}
