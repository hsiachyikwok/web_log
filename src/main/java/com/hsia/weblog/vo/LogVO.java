package com.hsia.weblog.vo;

import lombok.Data;

import java.util.Date;

/**
 * @
 */
@Data
public class LogVO {
    private Integer id;

    private String dirName;

    private String fileName;

    private String createTime;

    private String updateTime;
}
