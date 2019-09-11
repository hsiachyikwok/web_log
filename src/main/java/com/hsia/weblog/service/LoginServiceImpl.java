package com.hsia.weblog.service;

import com.hsia.weblog.api.ILoginService;
import com.hsia.weblog.entity.User;
import com.hsia.weblog.exception.UserException;
import com.hsia.weblog.mapper.UserMapper;
import com.hsia.weblog.util.EncrypUtil;
import com.hsia.weblog.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: hsia
 * @Date: 2018/2/17 下午10:29
 * @Description:
 */
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User login(LoginVo vo) throws Exception {
        User user = userMapper.selectByName(vo.getUsername());
        if (user == null || !EncrypUtil.getMD5Str(vo.getPassword()).equals(user.getPassword())) {
            throw UserException.USERNAME_PASSWORD_ERROR;
        }
        return user;
    }
}
