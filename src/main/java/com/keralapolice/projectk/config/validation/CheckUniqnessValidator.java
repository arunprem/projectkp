package com.keralapolice.projectk.config.validation;
import com.keralapolice.projectk.config.database.MySQLBaseDao;
import com.keralapolice.projectk.config.util.QueryUtilService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CheckUniqnessValidator implements ConstraintValidator<CheckUniqness,Object> {
    @Autowired
    MySQLBaseDao mySQLBaseDao;
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }
        int user = 0;
        user=mySQLBaseDao.queryNameForInteger("user.username.uniquecheck",new Object[]{value});
        if(user>0){
            return false;
        }else{
            return true;
        }





    }
}
