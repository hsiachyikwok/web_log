package com.hsia.weblog.controller.client;

import com.hsia.weblog.api.ILogService;
import com.hsia.weblog.vo.ResponseVo;
import com.hsia.weblog.vo.req.DataQueryReqVO;
import com.hsia.weblog.vo.req.LoggerQueryReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author hsia
 */
@RestController
@RequestMapping("/api")
@Api
@Slf4j
public class LoggerController {

    @Autowired
    private ILogService logService;

    @PostMapping("/logger/list")
    public ResponseVo queryLoggerList(@Valid @RequestBody LoggerQueryReqVO reqVO) {
        ResponseVo vo = new ResponseVo();
        vo.setBody(logService.getLogListByUserId(reqVO));
        return vo;
    }


    @PostMapping("/logger/detail")
    public ResponseVo queryLoggerDetail(@Valid @RequestBody DataQueryReqVO reqVO) {
        ResponseVo vo = new ResponseVo();
        vo.setBody(logService.getLogInfoById(reqVO.getId()));
        return vo;
    }

    @PostMapping("/logger/dir/list")
    public ResponseVo queryLoggerDirList(@Valid @RequestBody DataQueryReqVO reqVO) {
        ResponseVo vo = new ResponseVo();
        vo.setBody(logService.getDirList(reqVO.getId()));
        return vo;
    }
}
