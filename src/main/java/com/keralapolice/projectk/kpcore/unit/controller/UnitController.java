package com.keralapolice.projectk.kpcore.unit.controller;

import com.keralapolice.projectk.admin.rank.service.Rankservice;
import com.keralapolice.projectk.admin.rank.vo.RankVo;
import com.keralapolice.projectk.kpcore.unit.service.UnitService;
import com.keralapolice.projectk.kpcore.unit.vo.UnitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class UnitController {
    @Autowired
    UnitService unitService;

    @PostMapping("/addUnitDetails")
    public String addUnitDetails(HttpServletRequest request, @RequestBody @Valid UnitVo unit){
        try {
            unitService.createUnitNode(request,unit);
            return "added successfully";
        }catch (Exception e)
        {
            throw  e;
        }
    }

}
