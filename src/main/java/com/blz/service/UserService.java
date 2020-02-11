package com.blz.service;

import com.blz.domain.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();

    public User findByEmail(String email);

    public boolean login(String email, String password);

    public boolean changePwd(String email, String oldPwd,String newPwd);
}
