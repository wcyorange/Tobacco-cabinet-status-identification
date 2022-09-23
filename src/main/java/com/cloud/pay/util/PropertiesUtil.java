package com.cloud.pay.util;



import java.io.IOException;
import java.util.*;

public class PropertiesUtil {

    public static String findById(String name, String id) {
        Properties properties = new Properties();
        String resultName = "无此信息";
        //装载配置文件中的qq信息
        try {
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(name));
            resultName = new String(properties.getProperty(id).getBytes("ISO-8859-1"), "gbk");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultName;
    }

    public static HashMap<String,String> findAll(){
        String name="resource.properties";
        Properties properties = new Properties();
        String resultName = "无此信息";
        HashMap<String,String> hashMap = new HashMap<>();
        //装载配置文件中的qq信息
        try {
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(name));
            Enumeration<?> enumeration  =properties.propertyNames();
            while(enumeration.hasMoreElements()){
                String s = (String) enumeration.nextElement();
                String property = new String(properties.getProperty(s).getBytes("ISO-8859-1"), "gbk");
                hashMap.put(s,property);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(hashMap.isEmpty()){
            hashMap.put("0","无信息");
        }
        return hashMap;
    }
}
