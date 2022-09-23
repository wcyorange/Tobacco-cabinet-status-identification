package com.cloud.pay.config;

import java.util.ArrayList;
import java.util.List;

/**
*@Description:
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/28
*
**/        

public class DynamicDataSourceHolder {

    public static final String DEFAULT_DS = "one";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDB(String dbType) {
            contextHolder.set(dbType);
    }

    // 获取数据源名
    public static String getDB() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}
