package com.keralapolice.projectk.admin.rank.service;

import com.keralapolice.projectk.config.database.MySQLBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Rankservice {

    @Autowired
    MySQLBaseDao rankDao;


    public Integer rankList() {
        int count = 0;
        count= rankDao.queryNameForInteger("get.count.rank");
        return count;
    }
}
