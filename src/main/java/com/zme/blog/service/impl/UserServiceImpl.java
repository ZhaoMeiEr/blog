package com.zme.blog.service.impl;

import com.zme.blog.dao.UserRepository;
import com.zme.blog.po.User;
import com.zme.blog.service.UserService;
import com.zme.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @authon timber
 * @description 用户Service实现类
 * @date 2020/2/9 下午11:13
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @Author: timber
     * @Description: 根据用户名和密码检查用户
     * @Date: 2020/2/9 下午11:15
     * @param username
     * @param password
     * @Return User对象
     */
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
