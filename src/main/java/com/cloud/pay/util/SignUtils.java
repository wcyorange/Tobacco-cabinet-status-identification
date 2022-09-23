package com.cloud.pay.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 签名工具类，按字母升序排列，拼接key
 * @author zhaoyilong
 *
 */
public class SignUtils {
	private static BouncyCastleProvider provider = new BouncyCastleProvider();

	/**
	 * 直接拼接key在最后面
	 * 例：key=123456789的时候待签名的字符串为，aa=3&bb=1&cc=1&dd=20160426999123456789
	 * @param partnerKey
	 * @param params
	 * @return
	 */
	public  static String signParams(String partnerKey,Map params){
		//直接拼接key
		String tobesign = getToBeSign(params) + partnerKey;
		//System.out.println("tobesign:"+tobesign);
		return md5dataToSign(tobesign);
	}

	public static String md5dataToSign(String data){
		String algorithm = "MD5";
		try {
			//BouncyCastleProvider provider = new BouncyCastleProvider();
			MessageDigest md = MessageDigest.getInstance(algorithm, provider);
			byte[] rtn = md.digest(data.getBytes("UTF-8"));
			return StringUtils.byte2Hex(rtn).toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("不支持的算法:"+algorithm);
		}
	}


	public static String getToBeSign(Map params){
		TreeMap tm = new TreeMap();
		tm.putAll(params); //参数排序
		Set set = tm.entrySet();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Map.Entry e = (Map.Entry) iterator.next();
			String k = (String)e.getKey();
//			String v = (String)e.getValue();
			if(StringUtils.isNullOrEmpty(e.getValue()) || "sign".equals(k) || "mac".equals(k)){
				continue;
			}
			String v = "";
			if(e.getValue() instanceof JSONArray){
				v = getJsonArrayStr((JSONArray)e.getValue());
			}else if(e.getValue() instanceof JSONObject){
				v = getToBeSign((Map) e.getValue());
			}else{
				v = e.getValue().toString();
			}
			if(i != 0){
				sb.append("&");
			}
			sb.append(k+"="+v);
			i = i + 1;
		}
		return sb.toString();
	}

	public static String getJsonArrayStr(JSONArray a){
		String r = "";
		for(int j=0; j < a.size() ; j++){
			Object b = (Object) a.get(j);
			String v = "";
			if(b instanceof JSONArray){
				v = getJsonArrayStr((JSONArray)b);
			}else if(b instanceof JSONObject){
				v = getToBeSign((JSONObject)b);
			}else{
				v = b.toString();
			}
			if(j ==0){
				r = v;
			}else{
				r += "&" + v;
			}
		}
		return r;
	}

}
