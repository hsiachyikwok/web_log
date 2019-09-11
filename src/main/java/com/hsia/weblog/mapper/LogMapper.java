package com.hsia.weblog.mapper;

import com.github.pagehelper.PageInfo;
import com.hsia.weblog.entity.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);

    List<Log> selectLogListByUserId(@Param("userId") int id, @Param("dirName") String dirName, @Param("fileName") String fileName);

    List<String> selectDirList(@Param("userId") int id);
}