package com.cyh.service;

import com.cyh.pojo.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    User findByUsername(String username);

    User findById(Integer id);

}
