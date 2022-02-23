package com.springdemo.mvc.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourceCodeConstraintValidator implements ConstraintValidator< CourceCode,String>{
    private String courcePrefix;
    
    @Override
    public void initialize(CourceCode theCourceCode){
        courcePrefix = theCourceCode.value();
    }

    @Override
    public boolean isValid(String theCode,ConstraintValidatorContext theConstraintValidatorContext){
        boolean result;
        if(theCode!=null){
            result=theCode.startsWith(courcePrefix);
        }
        else{
            result =true;
        }
        return result;
    }
}
