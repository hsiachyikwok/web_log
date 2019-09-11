package com.hsia.weblog.mapper;

import com.hsia.weblog.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 查询当前选择 log
     *
     * @param userId
     * @return
     */
    String selectCurrentOptLog(@Param("userId") int userId);

    /**
     * 重置当前选择 log
     *
     * @param userId
     */
    void resetCurrentOptLog(@Param("userId") int userId);

    /**
     * 设置当前操作log file
     *
     * @param log
     * @param userId
     */
    void setCurrentOptLog(@Param("log") String log, @Param("userId") int userId);

    /**
     * 查询用户
     * @param username
     * @return
     */
    User selectByName(@Param("username") String username);
}