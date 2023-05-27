package com.thitiwas.demo.redisdemo.service;

import com.thitiwas.demo.redisdemo.model.User;
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

    List<User> users = new ArrayList<>();

    public void createUserForLoop(int target) {

        // int target = 5;
        log.info("create " + target + " user");
        IntStream.range(0, target).forEach(i -> addOneUser());
        log.info("users id list :{}", users.stream().map(User::getId).collect(Collectors.toList()));
    }

    private void addOneUser() {
        users.add(User.builder()
                .email("email" + getNewUserId())
                .createDate(Calendar.getInstance().getTime())
                .id(getNewUserId())
                .name("name" + getNewUserId())
                .build());
    }

    // unless ถ้าดึงมา ไม่ null จะ cache
    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    public Optional<User> findById(Long id) {
        return this.users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public List<User> findAll() {
        return this.users;
    }

    public User insertUser(User user) {
        user.setCreateDate(Calendar.getInstance().getTime());
        user.setId(getNewUserId());
        this.users.add(user);
        return user;
    }

    // remove cache by id
    @CacheEvict(value = "user", key = "#id")
    public void removeUser(Long id) throws IllegalAccessException {
        if (!this.users.removeIf(user -> user.getId().equals(id))) {
            throw new IllegalAccessException("can't find id: " + id);
        }
    }

    // remove cache all cache
    @CacheEvict(value = "user", allEntries = true)
    public void removeAll(){
        this.users = new ArrayList<>();
    }

    // update cache
    @CachePut(value = "user", key = "#user.id")
    public User updateUser(User user) {
        this.users.stream()
                .filter(user1 -> user.getId().equals(user1.getId()))
                .findFirst()
                .ifPresent(user1 -> {
                    user1.setName(user.getName());
                    user1.setEmail(user.getEmail());
                });
        return findById(user.getId()).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public Long getNewUserId() {
        return (long) (this.users.size() + 1);
    }
}
