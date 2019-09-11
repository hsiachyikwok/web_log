package com.hsia.weblog.mapper;

import com.hsia.weblog.entity.Log;
import com.hsia.weblog.entity.UserLogRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserLogRefMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLogRef record);

    int insertSelective(UserLogRef record);

    UserLogRef selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLogRef record);

    int updateByPrimaryKey(UserLogRef record);

}