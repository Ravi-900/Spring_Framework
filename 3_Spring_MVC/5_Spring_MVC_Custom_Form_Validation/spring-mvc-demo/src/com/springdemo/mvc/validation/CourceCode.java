package com.springdemo.mvc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy=CourceCodeConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )  // Can apply our annotation to a method or field
@Retention(RetentionPolicy.RUNTIME) //Retain this annotation in the Java class file - process it at runtime
public @interface CourceCode{
    
    // define default cource code
    public String value() default "LUV";

    // define default error message
    public String message() default "must start with LUV";
    
    // define default groups - can group related constraints
    public Class<?>[] groups() default{}; 
    
    // define default payloads - provide custom details about validation failure
    // (severity level,eeoe code etc)
    public Class<? extends Payload>[] payload() default{};
    
}