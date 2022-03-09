package com.keralapolice.projectk.admin.rank.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralapolice.projectk.admin.rank.service.Rankservice;
import com.keralapolice.projectk.admin.rank.vo.RankVo;
import com.keralapolice.projectk.admin.rank.vo.TestEncryption;
import com.keralapolice.projectk.config.encryption.AesEncryption.DataSecurityUtil;
import com.keralapolice.projectk.config.encryption.CAcertificate.CertificateManager;
import com.keralapolice.projectk.config.encryption.GenerateEncryptionSecurityService;
import com.keralapolice.projectk.config.encryption.GeneratePublicPrivateRsaKey;
import org.apache.poi.ss.formula.functions.Rank;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.Base64;

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

        @PostMapping("/encryptData")
        public String encryptUsingAes() throws CertificateException, IOException, CMSException {
        DataSecurityUtil dataSecurityUtil = new DataSecurityUtil();
            RankVo rankVo = new RankVo();
            rankVo.setRankId("1");
            rankVo.setCatId("2");
            rankVo.setStatus("sdfadfadfadsfadf");
            rankVo.setPostDesc("adfasdfadfasdf");
            ObjectMapper objectMapper = new ObjectMapper();
            String datas = objectMapper.writeValueAsString(rankVo);
        byte[] data = dataSecurityUtil.encryptData(datas.getBytes());
        return dataSecurityUtil.encode(data);
        }

    @PostMapping("/decryptUsingAes")
        public RankVo decryptUsingAes() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
            DataSecurityUtil dataSecurityUtil = new DataSecurityUtil();
            byte [] data = dataSecurityUtil.decryptData(Base64.getDecoder().decode("MIAGCSqGSIb3DQEHA6CAMIACAQAxggI9MIICOQIBADAhMBcxFTATBgNVBAMTDGtlcmFsYXBvbGljZQIGAX9tcLjvMA0GCSqGSIb3DQEBAQUABIICADMhsTQYX2TLuboSA6/dc9bJe57VaEOVy6c9nkJb4W9gAQ5axahxDLbHMQcS1ni/PX65Y9W0++D0ZUniuCxBXAcQXKhDSP+xKIXfqhwC377Z6n70fsPupRhaqhyak52XqrgBCmWZd0UIlGLqoeeKFK7p2Zt4nZW0cdCs6zkfy1MdZnUjYyezcVF34lHULFN8see2UH7oEqoE2L84cx2gl+yIFwjfseeOt8Vi0a4HYFpTNz1cJTnKM9jAYQCsu8J57UgAL+Y19dVhbXIBFKjgD2JyQEWGED7utLh5ni5dU9KBF7tdhxZ/lBN0gdfhwr9hyfcTIcH+LdGgcHSJeLAjp17Ea8dqX3GyOGC7NiojGwkgyA09YFpa4sS5z9QPLfSxViu9UTyZBF++1ISkDntP/OxrtjucaEgnH3aFm5MAa3/o2qsjxOZLkWBUel4TW/hp8xNnyVY03Xkqt5/G0cBRjvOLcKUPDnjZj/aaKIGhgx2s9NqU26hTWeeFFNyz50xZveLXSEvDXvw1TEjfrDvH6t8kIJLiBtlqsjpzTjsEr9yyc32cruPb4NW1BiK+hCDyzARHVBstXoR8sInH3bGB06BuVZO3l9CN8t8PwRNHj80sDkYapNzQOhXYqcCspJc51h8BINyz5e56zaEBFVnIQA4wK2k/hM+aT/64l5p1vHlSMIAGCSqGSIb3DQEHATAdBglghkgBZQMEASoEEDDVtok7E7/c/g33eBTj7CuAYPcOtwoklH9Vpvdqx1/TSCETejegzo8CDYnsFdF0oHcl7bBTpPRWgF/gT1qJA+z2mWHE3/JGjHMFeql1E1q/LBoYjYxDhKVlTon4xUUwHu/X4uTLB09GwghzysAspGpHyQAAAAAAAAAA"));
                byte[] decodeBytes= Base64.getDecoder().decode(dataSecurityUtil.encode(data));
        RankVo rankVo = objectMapper.readValue(decodeBytes,RankVo.class);
                return rankVo;
        }




}
