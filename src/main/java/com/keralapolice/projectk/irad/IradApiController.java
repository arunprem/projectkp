package com.keralapolice.projectk.irad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.keralapolice.projectk.admin.rank.vo.AuthVo;
import com.keralapolice.projectk.irad.service.IradApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class IradApiController {

    @Autowired
    IradApiService iradApiService;

    @RequestMapping(value = "/getIradDetials",method = RequestMethod.POST)
    public Object getApiCall(HttpServletRequest request) throws Exception {
        return iradApiService.getAccidentDetails();
    }
}
