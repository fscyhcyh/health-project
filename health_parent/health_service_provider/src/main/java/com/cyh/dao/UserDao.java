package com.cyh.dao;

import com.cyh.pojo.User;

public interface UserDao {
    User selectByUsername(String username);
    User selectById(Integer id);

}
