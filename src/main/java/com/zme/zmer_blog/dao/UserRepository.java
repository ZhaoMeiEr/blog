package com.zme.zmer_blog.dao;

import com.zme.zmer_blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: timber
 * @Description: 用户DAO接口
 * @Date: 2020/2/9 下午11:17
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String userName, String password);
}
