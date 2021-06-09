package com.cyh.controller;

import com.cyh.constant.MessageConstant;
import com.cyh.entity.Result;
import com.cyh.pojo.Menu;
import com.cyh.pojo.Permission;
import com.cyh.pojo.Role;
import com.cyh.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/getUsername")
    public Result getUsername(){
//        System.out.println("running...");
        try{
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(user);
            return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @RequestMapping("/getMenu")
    public Result getMenu(){
//        System.out.println("running...");
        try{
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(user);
            com.cyh.pojo.User userFromDb = userService.findByUsername(user.getUsername());
            LinkedHashSet<Menu> menuLinkedHashSet = new LinkedHashSet<>();
            for(Role role: userFromDb.getRoles()){
                for(Menu menu: role.getMenus()){
                    menuLinkedHashSet.add(menu);
                }
            }
            System.out.println(menuLinkedHashSet);
            return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,menuLinkedHashSet);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
