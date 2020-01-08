package com.qmr.controller;

import com.qmr.entity.ResultEntity;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResultEntity fileSizeException(HttpServletRequest request, MaxUploadSizeExceededException e) {
        System.out.println("用户上传大于20M的图片");
        return ResultEntity.fail("图片大小不能超过 20M 哦!");
    }

}
