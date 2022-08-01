package com.mmyrosh.springfilestoreaws.service;

import com.mmyrosh.springfilestoreaws.model.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);

    User updateUser(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    User getAuthUser();
}
