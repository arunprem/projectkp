package com.keralapolice.projectk.admin.rank.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralapolice.projectk.admin.rank.service.Rankservice;
import com.keralapolice.projectk.admin.rank.vo.RankVo;
import com.keralapolice.projectk.admin.rank.vo.TestEncryption;
import com.keralapolice.projectk.config.encryption.AesEncryption.DataSecurityUtil;
import com.keralapolice.projectk.config.encryption.CAcertificate.CertificateManager;
import com.keralapolice.projectk.config.encryption.GenerateEncryptionSecurityService;
import com.keralapolice.projectk.config.encryption.GeneratePublicPrivateRsaKey;
import org.bouncycastle.cms.CMSException;
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
        byte[] data = dataSecurityUtil.encryptData("arunprem adsfadsfadsf".getBytes());
        return dataSecurityUtil.encode(data);
        }

    @PostMapping("/decryptUsingAes")
        public String decryptUsingAes() throws Exception {
            DataSecurityUtil dataSecurityUtil = new DataSecurityUtil();
            byte [] data = dataSecurityUtil.decryptData(Base64.getMimeDecoder().decode("MIAGCSqGSIb3DQEHA6CAMIACAQAxggI9MIICOQIBADAhMBcxFTATBgNVBAMTDGtlcmFsYXBvbGljZQIGAX9oJAwwMA0GCSqGSIb3DQEBAQUABIICAGsfiE1gwzQ9ZfC094/R1eBBc+oyENSZrHwSj8x8s+boeMAl750+K++4PSVCgcJvhLAp1WgRJvZs3ZJIesfkXtp6aVwHa2DGVfRpQyFwX3a64MpaPFGM02icQ9CGTlJlvrhwIHC7/N5EmczXkidnHd/bKhhWYYOVqeATWeI0T8V0fWh0s4jzj9tsdat7VcETIYYRs23+E4Yh0ugjQRvgRTBqd61CDBS1L6Ff9NDvf4W4Fd5gnLRpdhohDL1koBVTvzH/3PzVnXWN+tJTjW2Zoee9t04MV2G83WZwz2uqg5pkyQpDIpvZgYiJzKM8F8seaOET59Tu1MXYr51zfGXeUZKMJSIIk8lXLJ9x3HlzSbGM3XD3ABCl6DXmtG9xauIBaY5gkhk+IYfda4HPaADFFaUrcja4p2xtPL1a9brSD0JombryFfFPIq3qzOJIEiQbprQ28Y+MJnpkWVwoWLIhvpXPtF2kpyXGpwGqPgp3Qh0rhY+yjvUhjTd2Y5a4Odq1R3DA8G4Qf6gBywpmH979ljmLIjkrj95UtxJ0fGqtS1tbWgM5CmFdE2M5JpIdayLvSXxkBiNZAfL/55SO1nEXJC1aos0kdXHaWLasJwX5zTvJDr8cJ0N9HCyaGyOVbE/fH/qS3/SHfDvLU0gR37nKASXOgByg4IDBmWJ1J277ViscMIAGCSqGSIb3DQEHATAdBglghkgBZQMEASoEEIHdT2zSzzo6E4bhBTviC9mAIP8gbKktEPj9PF4tYahUeqNQNHz7F/QqZ6cx5nqFnghDAAAAAAAAAAA="));
                return  dataSecurityUtil.encode(data);
        }




}
