package com.thitiwas.demo.redisdemo.service;

import com.thitiwas.demo.redisdemo.model.User;
import com.thitiwas.demo.redisdemo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUserForLoop(int target) {

        // int target = 5;
        log.info("create " + target + " user");
        IntStream.range(0, target).forEach(i -> addOneUser());
        log.info("users id list :{}", userRepository.getUsers().stream().map(User::getId).collect(Collectors.toList()));
    }

    private void addOneUser() {
        Date date = Calendar.getInstance().getTime();
        Long newUserId = getNewUserId();
        userRepository.addUser(User.builder()
                .email("email" + newUserId)
                .createDate(date)
                .id(newUserId)
                .name("name" + newUserId)
                .updateDate(date)
                .build());
    }

    // unless ถ้าดึงมา ไม่ null จะ cache
    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.getUsers();
    }

    public User insertUser(User user) {
        user.setCreateDate(Calendar.getInstance().getTime());
        user.setId(getNewUserId());
        user.setUpdateDate(Calendar.getInstance().getTime());
        userRepository.addUser(user);
        return user;
    }

    // remove cache by id
    @CacheEvict(value = "user", key = "#id")
    public void removeUser(Long id) throws IllegalAccessException {
        if (!userRepository.removeById(id)) {
            throw new IllegalAccessException("can't find id: " + id);
        }
    }

    // remove cache all cache
    @CacheEvict(value = "user", allEntries = true)
    public void removeAll() {
        userRepository.removeAll();
    }

    // update cache
    @CachePut(value = "user", key = "#user.id")
    public User updateUser(User user) {
        userRepository.updateUser(user);
        return findById(user.getId()).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public Long getNewUserId() {
        return userRepository.getNewUserId();
    }
}
