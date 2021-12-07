package com.keralapolice.projectk.login.service;


import com.keralapolice.projectk.config.database.MySQLBaseDao;
import com.keralapolice.projectk.login.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MySQLBaseDao myUserDao;


    public User SaveUser(User newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setName(newUser.getName());
        user.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

//        user.setUsername("admin");
//        user.setName("Arun");
//        user.setPassword(bCryptPasswordEncoder.encode("arun@7140"));
        myUserDao.queryNameForUpdate("user.insert.newUser", new Object[]{user.getUsername(), user.getPassword(), user.getName()});
        return user;
    }

}
