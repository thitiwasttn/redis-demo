package com.thitiwas.demo.redisdemo.config;

import com.thitiwas.demo.redisdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class UserConfig {

    @Autowired
    public UserConfig(UserService userService) {
        userService.createUserForLoop(5);
    }
}
