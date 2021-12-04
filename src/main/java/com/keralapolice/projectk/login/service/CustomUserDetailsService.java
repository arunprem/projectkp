package com.keralapolice.projectk.login.service;


import com.keralapolice.projectk.login.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setName("Arun");
        user.setUsername("arun@gmail.com");
        user.setPassword(bCryptPasswordEncoder.encode("arun@7140"));
        if(user==null) new UsernameNotFoundException("User nor Found");
        return user;

    }
    @Transactional
    public User loadUserById(Long id){
        User user = new User();
        user.setName("Arun");
        if(user==null) new UsernameNotFoundException("User nor Found");
        return user;
    }
}
