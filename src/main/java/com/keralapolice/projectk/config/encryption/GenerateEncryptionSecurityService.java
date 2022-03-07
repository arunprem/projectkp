package com.keralapolice.projectk.config.encryption;

import com.keralapolice.projectk.config.database.MySQLBaseDao;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class GenerateEncryptionSecurityService {

    @Autowired
    GeneratePublicPrivateRsaKey generatePublicPrivateRsaKey;

    @Autowired
    MySQLBaseDao mySQLBaseDao;

    public EncryptionDetailsVo registerApiUser(HttpServletRequest request, EncryptionDetailsVo encryptionDetailsVo) throws  Exception{
       if(encryptionDetailsVo.getAlgoType().equalsIgnoreCase("RSA")){
           generatePublicPrivateRsaKey.generateKeyPair();
           String privateKeyString= generatePublicPrivateRsaKey.encode(generatePublicPrivateRsaKey.getPrivateKey().getEncoded());
           String publicKeyString =  generatePublicPrivateRsaKey.encode(generatePublicPrivateRsaKey.getPublicKey().getEncoded());
           encryptionDetailsVo.setPrivateKeyString(privateKeyString);
           encryptionDetailsVo.setPublicKeyString(publicKeyString);
           encryptionDetailsVo.setClientSecret(getCustomUniquId());

       }
       mySQLBaseDao.queryNameForUpdate("insert.api.registration",new Object[]{
               encryptionDetailsVo.getClientId(),
               encryptionDetailsVo.getClientSecret(),
               encryptionDetailsVo.getAlgoType(),
               encryptionDetailsVo.getDepartmentName(),
               encryptionDetailsVo.getPublicKeyString(),
               encryptionDetailsVo.getPrivateKeyString(),
               LocalDateTime.now(),
               "Arun"
       });
       return encryptionDetailsVo;
    }

        public String getCustomUniquId() {
            LocalDateTime dtVal = LocalDateTime.now();
            return String.format("%d-%s-%d%d-%s-%d%d%d-%d", dtVal.getYear(),
                    RandomStringUtils.randomAlphanumeric(4), dtVal.getMonthValue(),
                    dtVal.getDayOfMonth(), UUID.randomUUID().toString().replace("-", "").substring(0, 6),
                    dtVal.getHour(), dtVal.getMinute(), dtVal.getSecond(), dtVal.getDayOfYear());
        }


    public String encrypt(HttpServletRequest request,String message) throws Exception {
        generatePublicPrivateRsaKey.initKeyFromUser(request.getHeader("client_id"),request.getHeader("client_secret"));
        byte[] messageToBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE,generatePublicPrivateRsaKey.getPrivateKey());
        byte[] encryptedBytes = cipher.doFinal(messageToBytes);
        return generatePublicPrivateRsaKey.encode(encryptedBytes);
    }

    public void printKeys(){
        System.err.println("public Key :"+ generatePublicPrivateRsaKey.encode(generatePublicPrivateRsaKey.getPublicKey().getEncoded()));
        System.err.println("private Key :"+ generatePublicPrivateRsaKey.encode(generatePublicPrivateRsaKey.getPrivateKey().getEncoded()));
    }



    public String decrypt(HttpServletRequest request,String encryptedMessage) throws  Exception{
        generatePublicPrivateRsaKey.initKeyFromUser(request.getHeader("client_id"),request.getHeader("client_secret"));
        byte[] encryptedBytes = generatePublicPrivateRsaKey.decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE,generatePublicPrivateRsaKey.getPublicKey());
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return new String(decryptedMessage,"UTF8");
    }

}
