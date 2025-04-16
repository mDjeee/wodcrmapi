package com.example.wodcrmapi.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface PaginationParams {
    String pageDefault() default "0";
    String sizeDefault() default "20";
    String sortByDefault() default "id";
    String sortDirectionDefault() default "asc";
}