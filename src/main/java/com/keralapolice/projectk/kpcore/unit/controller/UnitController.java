package com.keralapolice.projectk.kpcore.unit.controller;

import com.keralapolice.projectk.admin.rank.service.Rankservice;
import com.keralapolice.projectk.admin.rank.vo.RankVo;
import com.keralapolice.projectk.config.exception.ApiRequestException;
import com.keralapolice.projectk.config.exception.KpValidationException;
import com.keralapolice.projectk.kpcore.unit.service.UnitService;
import com.keralapolice.projectk.kpcore.unit.vo.UnitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
public class UnitController {
    @Autowired
    UnitService unitService;

    @PostMapping("/addUnitDetails")
    public ResponseEntity<?> addUnitDetails(HttpServletRequest request, @RequestBody @Valid UnitVo unit) throws KpValidationException{
        try {
            unitService.createUnitNode(request,unit);
            return new ResponseEntity<String>("Unit Created Successfully", HttpStatus.OK);
        }catch (Exception e)
        {
            throw  e;
        }
    }

    @PostMapping("/updateUnit")
    public ResponseEntity<?> updateUnitParent(HttpServletRequest request,@RequestBody @Valid UnitVo unit){
        try {
            unitService.moveUnit(request,unit);
            return new ResponseEntity<String>("Unit Updated Successfully", HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/deleteUnit/{unitId}")
    public Map<String,Object> deleteUnit(HttpServletRequest request, @RequestBody @Valid @PathVariable("unitId") int id){
        try{
            unitService.deleteNode(request,id);
            return Collections.singletonMap("message", "Deleted susccessfully");
        }catch (Exception e){
            throw new ApiRequestException("cannot delete this item");
        }
    }

}
