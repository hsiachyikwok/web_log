package com.hsia.weblog;


import com.hsia.weblog.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationTests {
    @Resource
    private UserMapper userMapper;

    @Test
    public void contextLoads() {

    }
}

