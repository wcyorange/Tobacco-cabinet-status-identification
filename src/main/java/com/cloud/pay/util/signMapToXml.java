package com.cloud.pay.util;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.cloud.pay.util.maptoxml.mapToXml;

public class signMapToXml {

    public static String  signMapToXml(Map<String, String> map, String key){

        /**sign签名的规则生成*/

        //按字典顺序排序
        List<Map.Entry<String, String>> infoIds = new ArrayList<>(map.entrySet());
        Collections.sort(infoIds, (o1, o2) -> (o1.getKey()).compareTo(o2.getKey()));
        //使用&符号进行频率
        String sbR = getStringBuffer(infoIds) + "&key="+key;
        //进行MD5加密之后  转大写
        String sign = md5code.MD5(sbR);
        map.put("sign", sign);
        String toXml = "";
        try {
            toXml = mapToXml(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toXml;
    }
    /**
     * 拼接
     * @param infoIds
     * @return
     */
    @NonNull
    public static String getStringBuffer(List<Map.Entry<String, String>> infoIds) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, String> stringStringEntry = infoIds.get(i);
            if (stringStringEntry.getKey() == null) {
                stringStringEntry.getKey();
            }
            String key = stringStringEntry.getKey();
            Object val = stringStringEntry.getValue();
            if (i != infoIds.size() - 1) {
                if (val != null && !val.toString().equals("")) {
                    sb.append(key).append("=").append(val).append("&");
                }
            } else {
                if (val != null && !val.toString().equals("")) {
                    sb.append(key).append("=").append(val);
                }
            }
        }
        return sb.toString();

    }
}
