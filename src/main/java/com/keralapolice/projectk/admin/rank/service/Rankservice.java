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

}
