package com.thitiwas.demo.redisdemo.controller;

import com.thitiwas.demo.redisdemo.model.User;
import com.thitiwas.demo.redisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<User>> findAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id){
        return ResponseEntity.of(userService.findById(id));
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }
}
