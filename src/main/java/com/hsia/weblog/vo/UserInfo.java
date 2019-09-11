package com.hsia.weblog.vo;

import lombok.ToString;

import java.security.Principal;

/**
 * @author hsia
 * 用户信息
 */
@ToString
public class UserInfo implements Principal {

    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
