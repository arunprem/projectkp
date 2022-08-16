package com.keralapolice.projectk.admin.rank.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralapolice.projectk.admin.rank.service.Rankservice;
import com.keralapolice.projectk.admin.rank.vo.AuthVo;
import com.keralapolice.projectk.admin.rank.vo.RankVo;
import com.keralapolice.projectk.admin.rank.vo.TestEncryption;
import com.keralapolice.projectk.config.encryption.AesEncryption.DataSecurityUtil;
import com.keralapolice.projectk.config.encryption.CAcertificate.CertificateManager;
import com.keralapolice.projectk.config.encryption.GenerateEncryptionSecurityService;
import com.keralapolice.projectk.config.encryption.GeneratePublicPrivateRsaKey;
import org.apache.poi.ss.formula.functions.Rank;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.util.encoders.UTF8;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

        @RequestMapping(value = "/getApiDetails",method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        public Object getApiCall(HttpServletRequest request, AuthVo authVo) throws JsonProcessingException {
            return rankservice.testRestTemplate(authVo);
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

//        @PostMapping("/encryptData")
//        public String encryptUsingAes() throws CertificateException, IOException, CMSException {
//        DataSecurityUtil dataSecurityUtil = new DataSecurityUtil();
//            RankVo rankVo = new RankVo();
//            rankVo.setRankId("1");
//            rankVo.setCatId("2");
//            rankVo.setStatus("sdfadfadfadsfadf");
//            rankVo.setPostDesc("adfasdfadfasdf");
//            ObjectMapper objectMapper = new ObjectMapper();
//            String datas = objectMapper.writeValueAsString(rankVo);
//        byte[] data = dataSecurityUtil.encryptData(datas.getBytes());
//        return dataSecurityUtil.encode(data);
//        }

//    @PostMapping("/decryptUsingAes")
//        public String decryptUsingAes() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//            DataSecurityUtil dataSecurityUtil = new DataSecurityUtil();
//            byte [] data = dataSecurityUtil.decryptData(Base64.getDecoder().decode("MIAGCSqGSIb3DQEHA6CAMIACAQAxggI9MIICOQIBADAhMBcxFTATBgNVBAMTDGtlcmFsYXBvbGljZQIGAYCdKMczMA0GCSqGSIb3DQEBAQUABIICADCCunuv1uFf6BU7zVrp5hFCDM9XCxeT4TKYY+NmNpvmrmBZvNJxQiLWjK3HRl4Kv2d4ydH/WGYrhnjH05OQm6H8I82Zb1Du+XlyY68CcoGtk5Jj4YwydFtHZO7JlD6+1x7kKJ/xPMoieONlI7vSfkPD2hfgTqMIBahHIEZP2QKgFMuj+Hldg4eCDj8z/JxdKDdH/2B7Wfi4S6YKt96IbZhI3kLOtLcVKqQlFEhBfKsZKIxJAVkpEiWBdMdjUJckBEGXjri9mvtbDqcsk8b3fw7H9dSrUU7zhptJEna28PKXQ3PWFy39vZn7VdnTVm8ijHg6iwLJ6kTBFUU/Gk3Ptqq21EQCyBJKVqf80TSR4Pw0263qxOGiyidNc+AkUXIu9+ZcrcGjcNklAnQSt0i0GNu8MMEjiK79evVRgnDMAWBPwySAyzoyWXH1nRfWkvM1GDcot6OIlLrte+g3kPwCl7YMvMZq2Z4ygvWng7Xl4ou0KIB7yty/KlUubgj7z7Ioc1ACfOSiB7LVVmRHdiI9DYD68taN7B63gSJdVfiTW9IgUXRMBP12Eg7NQM/7csauxF6LArZXfwZfRKhMEsRycMp+Rxk9BIM2gc/M0Rtu8K2xTNzieM8tW/RAqvctO27v2OxgbQRZ1/1LmBfmP/dLvxmEzjCHOaM01PikWGXr8OEMMIAGCSqGSIb3DQEHATAdBglghkgBZQMEASoEEHMYkfYOnIG6Et0bMkYrVkiggARQZr9uH5BHUhGbGz4oe7ZhD87T6rTOtBoQi9A6v0D7fmxu4ti7//ZvxIrsf+98Em3ZD6p7qTAhxt1x6yqV9q1rXvoYhGgR/s/tgeSehwVsQgsAAAAAAAAAAAAA"));
//                byte[] decodeBytes= Base64.getDecoder().decode(dataSecurityUtil.encode(data));
////                RankVo rankVo = objectMapper.readValue(decodeBytes,RankVo.class);
////                return rankVo;
//        String decryptedString = new String(decodeBytes);
//        return decryptedString;
//        }

}
