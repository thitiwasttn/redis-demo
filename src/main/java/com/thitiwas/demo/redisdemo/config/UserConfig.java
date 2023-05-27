package com.thitiwas.demo.redisdemo.config;

import com.thitiwas.demo.redisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    private final UserService userService;

    @Autowired
    public UserConfig(UserService userService) {
        this.userService = userService;
        this.userService.createUser5();
    }
}
