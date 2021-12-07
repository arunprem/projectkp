package com.keralapolice.projectk.login.service;


import com.keralapolice.projectk.config.database.MySQLBaseDao;
import com.keralapolice.projectk.config.util.QueryUtilService;
import com.keralapolice.projectk.login.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    QueryUtilService myDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user =(User) myDao.getObject("user.select.userbyname",new Object[]{username},User.class);
        if(user==null) throw new UsernameNotFoundException("User nor Found");
        return user;
    }

    @Transactional
    public User loadUserById(Long id){
        User user = new User();
        user = (User) myDao.getObject("user.select.userbyid",new Object[]{id},User.class);
        if(user==null) new UsernameNotFoundException("User nor Found");
        return user;
    }
}
