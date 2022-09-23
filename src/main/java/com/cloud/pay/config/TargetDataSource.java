package com.cloud.pay.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/**
*@Description:自定义标签
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/28
*
**/

public @interface TargetDataSource {
    String value();
}