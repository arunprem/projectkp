package com.keralapolice.projectk.admin.rank.controller;


import com.keralapolice.projectk.admin.rank.service.Rankservice;
import com.keralapolice.projectk.admin.rank.vo.RankVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class Rankcontroller {

    @Autowired
    Rankservice rankservice;

        @PostMapping("/getrankcount")
        public Integer tableCount(){
        return rankservice.rankList();

        }

        @PostMapping("/addPostDetails")
        public String addPostDetails(HttpServletRequest request, @RequestBody @Valid RankVo rankVo){
            try {
                return "added successfully";
            }catch (Exception e)
            {
                throw  e;
            }
        }

}