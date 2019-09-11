package com.hsia.weblog.exception;

/**
 * @author hsia
 */
public class LogException extends GlobalException {
    public LogException(String code, String message) {
        super(code, message);
    }

    public static final LogException LOG_FILE_NOT_SELECTED = new LogException("200","请选择日志文件！");

}
