package com.zme.blog.service;

import com.zme.blog.po.User;

/**
 * @Author: timber
 * @Description: 用户Service接口
 * @Date: 2020/2/9 下午11:12
 */
public interface UserService {
    /**
     * @Author: timber
     * @Description: 根据用户名和密码检查用户
     * @Date: 2020/2/9 下午11:15
     * @param username
     * @param password
     * @Return
     */
    User checkUser(String username, String password);
}
