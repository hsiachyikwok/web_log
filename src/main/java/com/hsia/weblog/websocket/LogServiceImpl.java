package com.hsia.weblog.websocket;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsia.weblog.api.ILogService;
import com.hsia.weblog.entity.Log;
import com.hsia.weblog.mapper.LogMapper;
import com.hsia.weblog.mapper.UserLogRefMapper;
import com.hsia.weblog.vo.LogVO;
import com.hsia.weblog.vo.req.LoggerQueryReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hsia
 */
@Slf4j
@Service
public class LogServiceImpl implements ILogService {

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private UserLogRefMapper userLogRefMapper;


    @Override
    public Log getLogInfoById(int id) {
        return logMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<LogVO> getLogListByUserId(LoggerQueryReqVO reqVO) {
        PageInfo<Log> pageInfo = PageHelper.startPage(reqVO.getPage(), reqVO.getRows()).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                logMapper.selectLogListByUserId(reqVO.getId(), reqVO.getDirName(), reqVO.getFileName());
            }
        });return copyResult(pageInfo);
    }

    private PageInfo<LogVO> copyResult(PageInfo<Log> pageInfo) {
        List<Log> logList = pageInfo.getList();
        List<LogVO> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(logList)) {
            for (Log log : logList) {
                LogVO vo = new LogVO();
                BeanUtils.copyProperties(log, vo);
                DateFormat formater = new SimpleDateFormat("yy-MM-dd:HH:mm");
                vo.setCreateTime(formater.format(log.getCreateTime()));
                vo.setUpdateTime(formater.format(log.getUpdateTime()));
                result.add(vo);
            }
        }
        PageInfo<LogVO> pageResult = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, pageResult);
        pageResult.setList(result);
        return pageResult;
    }

    @Override
    public List<String> getDirList(int id) {
        return logMapper.selectDirList(id);
    }
}
