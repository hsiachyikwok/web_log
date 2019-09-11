package com.hsia.weblog.api;

import com.github.pagehelper.PageInfo;
import com.hsia.weblog.entity.Log;
import com.hsia.weblog.vo.LogVO;
import com.hsia.weblog.vo.req.LoggerQueryReqVO;

import java.util.List;

public interface ILogService {
    /**
     * 根据id查询 log信息
     *
     * @param id
     * @return
     */
    Log getLogInfoById(int id);

    /**
     * 根据用户userId查询文件列表
     *
     * @param id
     * @return
     */
    PageInfo<LogVO> getLogListByUserId(LoggerQueryReqVO reqVO);

    /**
     * 查询文件目录列表
     *
     * @return
     */
    List<String> getDirList(int id);

}
