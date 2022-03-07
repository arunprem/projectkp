package com.keralapolice.projectk.admin.rank.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralapolice.projectk.admin.rank.service.Rankservice;
import com.keralapolice.projectk.admin.rank.vo.RankVo;
import com.keralapolice.projectk.admin.rank.vo.TestEncryption;
import com.keralapolice.projectk.config.encryption.CAcertificate.CertificateManager;
import com.keralapolice.projectk.config.encryption.GenerateEncryptionSecurityService;
import com.keralapolice.projectk.config.encryption.GeneratePublicPrivateRsaKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.*;
import java.security.cert.CertificateEncodingException;

@RestController
public class Rankcontroller {

    @Autowired
    GenerateEncryptionSecurityService generateEncryptionSecurityService;

    @Autowired
    Rankservice rankservice;

    @Autowired
    GeneratePublicPrivateRsaKey generatePublicPrivateRsaKey;

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
        @PostMapping("/getEncryptedData/{dataString}")
        @ResponseBody
        public String checkEncryption(HttpServletRequest request,@PathVariable("dataString") String message) throws Exception {
            RankVo rankVo = new RankVo();
            rankVo.setRankId("1");
            rankVo.setCatId("2");
            rankVo.setStatus("sdfadfadfadsfadf");
            rankVo.setPostDesc("adfasdfadfasdf");
            ObjectMapper objectMapper = new ObjectMapper();
            return generateEncryptionSecurityService.encrypt(request,objectMapper.writeValueAsString(rankVo));
        }

        @PostMapping("/getDecriptData")
        public RankVo decriptionCheck(HttpServletRequest request, @RequestBody TestEncryption testEncryption) throws Exception{
           String output= generateEncryptionSecurityService.decrypt(request,testEncryption.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
           RankVo rankVo = objectMapper.readValue(output,RankVo.class);

            return rankVo;
        }

         @PostMapping("/testCertificate")
        public void testCertificate() throws CertificateEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
            CertificateManager certificateManager = new CertificateManager();
            certificateManager.GenerateCertificate();
        }




}
