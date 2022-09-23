package com.cloud.pay.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author 宁志洋
 * @create 2020/10/26 9:36
 */
public class HttpPostUtil {

    public static Map<String, String> doPost(Map<String, String> param) {
        String url = "http://127.0.0.1:5000/CheckCamera/Main";
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        Map<String, String> map = new HashMap<>();
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            if (statusCode == HttpStatus.SC_OK) {
                System.out.println(resultString);
                System.out.println("连接成功！！！ ----->  开始返回参数**");
                JSONObject jsTemp = JSONObject.parseObject(resultString);
                map.put("code", jsTemp.getString("id"));
                map.put("status",jsTemp.getString("status"));
                map.put("message", jsTemp.getString("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(simpleDateFormat.format(new Date()));
        return map;
    }
}
