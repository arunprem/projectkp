package com.keralapolice.projectk.login.Controller;

import com.keralapolice.projectk.config.database.MySQLBaseDao;
import com.keralapolice.projectk.config.reportexport.ExcelExporter;
import com.keralapolice.projectk.config.security.JwtTokenProvider;
import com.keralapolice.projectk.config.util.QueryUtilService;
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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    MySQLBaseDao mySQLBaseDao;



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(HttpServletRequest request, @RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new TokenResponse(true, jwt));
        }catch (ValidationException e){
        throw e;
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(HttpServletRequest request, @RequestBody @Valid User user){
        userService.SaveUser(user);
        return ResponseEntity.ok("user created");
    }



    @PostMapping("/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException, IllegalAccessException {
        ExcelExporter excelExporter = new ExcelExporter();
        List<String> headding = new ArrayList<>();
        headding.add("test1");
        headding.add("test2");
        headding.add("test3");
        List<Map<String, Object>>   user =  mySQLBaseDao.queryNameForList("get.all.users",new Object[]{1});
        excelExporter.export(response,headding,user,"userdetails","title of report","sheetname");
    }

}
