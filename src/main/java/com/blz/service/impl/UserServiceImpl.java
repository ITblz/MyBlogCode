package com.blz.service.impl;

import com.blz.dao.UserDao;
import com.blz.domain.User;
import com.blz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public List<User> findAll() {

        System.out.println("业务层：查询用户信息...");
        return userDao.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean login(String email, String password) {
         Integer res = userDao.login(email, password);
         if(res == null || res == -1){
             return false;
         }
         if (res == 1){
             return true;
         }
         return false;
    }

    @Override
    public boolean changePwd(String email, String oldPwd,String newPwd) {
        Integer res = userDao.login(email, oldPwd);
        System.out.println(res);
        System.out.println(oldPwd);
        if (res == 1){
            Integer result =  userDao.changePwd(email, newPwd);
            System.out.println("更改结果！"+result);
            if(result == null || result == -1){
                return false;
            }else {
                return true;
            }
        }
        return false;
    }
}
