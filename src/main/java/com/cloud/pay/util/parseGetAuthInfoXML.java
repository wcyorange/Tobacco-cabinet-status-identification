package com.cloud.pay.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class parseGetAuthInfoXML {
    public  static String parseGetAuthInfoXML(String resultText, String indexText) throws Exception {
        Document doc= DocumentHelper.parseText(resultText);
        Element root = doc.getRootElement();
        Element result = root.element(indexText);
        String json = null;
        if(result!=null){
            json   = result.getTextTrim();
        }
        return json;

    }
}
