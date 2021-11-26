package com.keralapolice.projectk.kpcore.unit.controller;

import com.keralapolice.projectk.admin.rank.service.Rankservice;
import com.keralapolice.projectk.admin.rank.vo.RankVo;
import com.keralapolice.projectk.kpcore.unit.service.UnitService;
import com.keralapolice.projectk.kpcore.unit.vo.UnitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/updateUnit")
    public String updateUnitParent(HttpServletRequest request,@RequestBody @Valid UnitVo unit){
        try {
            unitService.moveUnit(request,unit);
            return "update successfully";
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/deleteUnit/{unitId}")
    public String deleteUnit(HttpServletRequest request,@RequestBody @Valid @PathVariable("unitId") int id){
        try{
            unitService.deleteNode(request,id);
            return "delete successfully";
        }catch (Exception e){
            throw e;
        }
    }

}
