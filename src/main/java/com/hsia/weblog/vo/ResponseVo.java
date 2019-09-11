package com.hsia.weblog.vo;

import lombok.Data;

/**
 * @author: hsia
 * @Date: 2018/1/24 下午5:38
 * @Description: 通用返回对象,返回值包装在body中
 */
@Data
public class ResponseVo {
    public static final String ERROR_MESSAGE="system error";

    public static final String PARAM_VALIDATE_ERROR="param validate error";

    public static final String SUCCESS_MESSAGE="success";

    private String code = "0";

    private String message = SUCCESS_MESSAGE;

    private Object body;

}
