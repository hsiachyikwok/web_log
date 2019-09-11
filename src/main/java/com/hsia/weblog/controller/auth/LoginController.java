package com.hsia.weblog.controller.auth;

import com.hsia.weblog.api.ILogService;
import com.hsia.weblog.api.ILoginService;
import com.hsia.weblog.entity.User;
import com.hsia.weblog.util.SessionUtil;
import com.hsia.weblog.vo.LoginVo;
import com.hsia.weblog.vo.ResponseVo;
import com.hsia.weblog.vo.SessionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


/**
 * @author: hsia
 * @Date: 2018/1/24 下午5:51
 * @Description: 后台登录接口
 */
@RestController
@Slf4j
@Api("登录")
@RequestMapping(value = "user")
public class LoginController {
    @Autowired
    private ILoginService loginService;

    @Autowired
    private ILogService logService;

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseVo login(@Valid @RequestBody LoginVo loginVo, HttpServletRequest httpServletRequest) throws Exception {
        ResponseVo vo = new ResponseVo();
        User user = loginService.login(loginVo);
        log.info("user:{}",user);
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            session = httpServletRequest.getSession();
        }
        loginVo.setSession(session);
        //生成session
        SessionUtil.generateSession(loginVo);
        SessionInfo sessionInfo = SessionUtil.getSessionInfo(session);
        sessionInfo.setUserId(user.getId());
        sessionInfo.setIsAdmin(user.getIsAdmin());
        vo.setBody(sessionInfo);
        return vo;
    }


    @ApiOperation("登出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseVo logout(HttpSession session) {
        log.info("****{}:logout****", SessionUtil.getSessionInfo(session).getUsername());
        SessionUtil.logout(session);
        return new ResponseVo();
    }
}
