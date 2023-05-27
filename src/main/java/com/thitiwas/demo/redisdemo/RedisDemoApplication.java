package com.thitiwas.demo.redisdemo;

import com.thitiwas.demo.redisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisDemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}

}
