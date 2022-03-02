package com.keralapolice.projectk.admin.rank.service;

import com.keralapolice.projectk.config.database.MySQLBaseDao;
import com.keralapolice.projectk.config.encryption.GeneratePublicPrivateRsaKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class Rankservice {

    @Autowired
    MySQLBaseDao rankDao;

    @Autowired
    GeneratePublicPrivateRsaKey generatePublicPrivateRsaKey;


    public Integer rankList() {
        int count = 0;
        count= rankDao.queryNameForInteger("get.count.rank");
        return count;
    }


    public String encrypt(String message) throws Exception {

        byte[] messageToBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE,generatePublicPrivateRsaKey.getPublicKey());
        byte[] encryptedBytes = cipher.doFinal(messageToBytes);
        return encode(encryptedBytes);
    }

    public String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    public String decrypt(String encryptedMessage) throws  Exception{
        byte[] encryptedBytes = decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE,generatePublicPrivateRsaKey.getPrivateKey());
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return new String(decryptedMessage,"UTF8");
    }

    public byte[] decode(String data){
        return  Base64.getDecoder().decode(data);
    }
}
