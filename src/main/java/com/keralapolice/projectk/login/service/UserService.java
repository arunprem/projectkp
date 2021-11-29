package com.keralapolice.projectk.login.service;


import com.keralapolice.projectk.login.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User SaveUser(User newUser){
        User user = new User();
        user.setUsername("arun");
        user.setPassword(bCryptPasswordEncoder.encode("arun@710"));
        user.setName("Arunprem");
        return user;
    }

}
