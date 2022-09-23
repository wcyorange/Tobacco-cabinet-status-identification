package com.cloud.pay.util;

import com.alibaba.fastjson.JSONObject;
//import com.alibaba.*
import com.cloud.pay.config.BeanContext;
import com.cloud.pay.entity.Camera;
import com.cloud.pay.entity.ThinkEquipment;
import com.cloud.pay.entity.ThinkEquipmentMessage;
import com.cloud.pay.service.ThinkEquipmentMessageService;
import com.cloud.pay.service.ThinkEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author:宁志洋
 * @Date:2020/9/20 20:16
 */

public class MyThread extends Thread {

    private ThinkEquipmentService thinkEquipmentService;
    private Integer id;
    private String ip;
    private String username;
    private String password;
    private String name;
    private String code;
    private String jpg;

    public void init(Integer id, String ip, String username, String password, String name,String code) {
        this.id = id;
        this.ip = ip;
        this.username = username;
        this.password = password;
        this.name = name;
        this.code = code;
    }


    @Override
     public void run() {
        while(true){
            this.thinkEquipmentService = BeanContext.getApplicationContext().getBean(ThinkEquipmentService.class);
            int i = 1;
            String jpgName;
            String normalName = "";
            String jpgName2;
            String abnormalName = "";
            String normalDir = "D:\\HkvsDir\\normal\\";
            String abNormalDir = "D:\\HkvsDir\\abnormal\\";

            Camera camera = new Camera();
            camera.setIp(this.ip);
            camera.setPort((short) 8000);
            camera.setUsername(this.username);
            camera.setPassword(this.password);
            camera.setInfo(this.name);
            camera.setDefaultID(0);
            SnapShot snapShot = new SnapShot();
            ThinkEquipment thinkEquipment = new ThinkEquipment();
            List<String> list = new ArrayList<>();
            while (i < 6){

                jpgName = normalDir + System.currentTimeMillis() + "_" + this.name + "_" + this.ip + "_" + i + ".jpg";
                jpgName2 = abNormalDir + System.currentTimeMillis() + "_" + this.name + "_" + this.ip + "_" + i + ".jpg";
                normalName += jpgName + ",";
                abnormalName += jpgName2 + ",";
                System.out.println("截图中.........");
                System.out.println("单------" + jpgName);
                snapShot.getHkwsPic(camera, 1, jpgName);
                if (i == 5){
                    list.add(normalName);
                    System.out.println(normalName);

                    Map<String, String> paramMap = new HashMap();
                    paramMap.put("id", this.code);
                    paramMap.put("code", "");
                    paramMap.put("address", normalName);

                    Map<String, String> postMap = HttpPostUtil.doPost(paramMap);
                    thinkEquipment.setCode(postMap.get("code"));
                    System.err.println(thinkEquipment.getCode());
                    thinkEquipment.setStatusMsg(postMap.get("message"));
                    thinkEquipmentService.updateMessageByCode(thinkEquipment);

                    if ((postMap.get("status")).equals(String.valueOf(10005))) {
                        ThinkEquipmentMessage equipmentMessage = new ThinkEquipmentMessage();
                        equipmentMessage.setEqId(this.id);
                        equipmentMessage.setImgUrl(abnormalName);
                        PostThread postThread = new PostThread();
                        postThread.init(camera, this.id, abnormalName,equipmentMessage);
                        Thread thread = new Thread(postThread);
                        thread.start();
                    }

                }
                i++;
            }
        }
    }
}
