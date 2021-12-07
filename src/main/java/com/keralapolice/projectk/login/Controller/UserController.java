package com.keralapolice.projectk.login.Controller;

import com.keralapolice.projectk.config.security.JwtTokenProvider;
import com.keralapolice.projectk.login.service.UserService;
import com.keralapolice.projectk.login.vo.LoginRequest;
import com.keralapolice.projectk.login.vo.TokenResponse;
import com.keralapolice.projectk.login.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(HttpServletRequest request, @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
           );


        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new TokenResponse(true,jwt));
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(HttpServletRequest request,@RequestBody @Valid User user){
        userService.SaveUser(user);
        return ResponseEntity.ok("user created");
    }

}
