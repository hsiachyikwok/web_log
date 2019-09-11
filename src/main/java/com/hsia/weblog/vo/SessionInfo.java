package com.hsia.weblog.vo;

import lombok.Data;

/**
 * @author: hsia
 * @Date: 2018/2/28 下午2:37
 * @Description:
 */
@Data
public class SessionInfo {
    /**
     * sessionId
     */
    private String sessionId;
    /**
     * userId
     */
    private int userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * token
     */
    private String token;

    private Integer isAdmin;
}
