package com.cyh.security;

import com.cyh.pojo.Menu;
import com.cyh.pojo.Permission;
import com.cyh.pojo.Role;
import com.cyh.pojo.User;
import com.cyh.service.MenuService;
import com.cyh.service.PermissionService;
import com.cyh.service.RoleService;
import com.cyh.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("SpringSecurityUserService")
public class SpringSecurityUserService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDb = userService.findByUsername(username);
        if (userFromDb == null) {
            return null;
        }
//        System.out.println(userFromDb);
//        System.out.println(bCryptPasswordEncoder.encode("admin"));
        //roles and permission
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        for (Role role : userFromDb.getRoles()) {
            Set<Permission> permissionSet = role.getPermissions();
            LinkedHashSet<Menu> menuSet = role.getMenus();
            System.out.println(menuSet);
            role.setMenus(menuSet);
            role.setPermissions(permissionSet);
            for (Permission permission : permissionSet) {
                authorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username, userFromDb.getPassword(), authorityList);

//        System.out.println(userFromDb);
//        System.out.println(userDetails);

        return userDetails;
    }
}
