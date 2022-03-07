package com.keralapolice.projectk.config.encryption;

import com.keralapolice.projectk.admin.rank.vo.RankVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class GenerateSecurityEncryptionController {

    @Autowired
    GenerateEncryptionSecurityService generateEncryptionSecurityService;
    @PostMapping("/registerApi")
    public EncryptionDetailsVo registerAPi(HttpServletRequest request, @RequestBody @Valid EncryptionDetailsVo encryptionDetailsVo) throws Exception {
        try {
            return generateEncryptionSecurityService.registerApiUser(request,encryptionDetailsVo);
        }catch (Exception e)
        {
            throw  e;
        }
    }
}
