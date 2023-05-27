package com.thitiwas.demo.redisdemo.repository;

import com.thitiwas.demo.redisdemo.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    final List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User u) {
        users.add(u);
    }

    public boolean removeById(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    public void removeAll() {
        users.clear();
    }

    public void updateUser(User user) {
        users.stream()
                .filter(user1 -> user.getId().equals(user1.getId()))
                .findFirst()
                .ifPresent(user1 -> {
                    user1.setName(user.getName());
                    user1.setEmail(user.getEmail());
                    user1.setUpdateDate(Calendar.getInstance().getTime());
                });
    }

    public Long getNewUserId() {
        return (long) (users.size() + 1);
    }

    public Optional<User> findById(Long id) {
        return this.users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }
}
