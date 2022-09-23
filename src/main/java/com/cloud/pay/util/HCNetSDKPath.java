package com.cloud.pay.util;

import java.io.UnsupportedEncodingException;

/**
 * @Author:宁志洋
 * @Date:2020/10/10 21:59
 */
public class HCNetSDKPath {
    public static String DLL_PATH;
    static {
        String path = (HCNetSDKPath.class.getResource("/").getPath()).replaceAll("%20", " ").substring(1).replace("/",
                "\\");
        try {
            DLL_PATH = java.net.URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
