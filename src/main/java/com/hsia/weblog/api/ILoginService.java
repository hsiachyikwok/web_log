package com.hsia.weblog.api;

import com.hsia.weblog.entity.User;
import com.hsia.weblog.vo.LoginVo;

/**
 * @author: hsia
 * @Date: 2018/2/17 下午10:20
 * @Description:
 */
public interface ILoginService {
    /**
     * 登录接口
     * @param vo
     * @return token
     */
    User login(LoginVo vo) throws Exception;
}
