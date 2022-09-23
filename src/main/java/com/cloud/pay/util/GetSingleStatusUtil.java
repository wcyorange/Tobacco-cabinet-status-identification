package com.cloud.pay.util;

import com.cloud.pay.entity.Camera;
import com.cloud.pay.entity.ThinkEquipment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 宁志洋
 * @date 2020/12/13
 */
public class GetSingleStatusUtil {
    public static Map<String,String> getSingleStatus(ThinkEquipment thinkEquipment){
        Map<String,String> map = new HashMap<>();
        String jpgName;
        String normalName = "";
        String normalDir = "D:\\HkvsDir\\normal\\";

        Camera camera = new Camera();
        camera.setIp(thinkEquipment.getIp());
        camera.setPort((short) 8000);
        camera.setUsername(thinkEquipment.getUsername());
        camera.setPassword(thinkEquipment.getPassword());
        camera.setInfo(thinkEquipment.getName());
        camera.setDefaultID(0);
        SnapShot snapShot = new SnapShot();
        for (int i = 1;i < 6;i++){
            jpgName = normalDir + System.currentTimeMillis() + "_" + thinkEquipment.getName() + "_" + thinkEquipment.getIp() + "_" + i + ".jpg";
            normalName += jpgName + ",";
            System.out.println("截图中.........");
            System.out.println("单------" + jpgName);
            snapShot.getHkwsPic(camera, 1, jpgName);
            if (i == 5){
                Map<String, String> paramMap = new HashMap();
                paramMap.put("id", thinkEquipment.getCode());
                paramMap.put("code", "");
                paramMap.put("address", normalName);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                System.out.println(simpleDateFormat.format(new Date()));
                Map<String, String> postMap = HttpPostUtil.doPost(paramMap);
                map.put("status",postMap.get("status"));
                map.put("message",postMap.get("message"));
                System.out.println("postMap-->"+postMap.get("message"));
            }
        }
        System.out.println("map-->"+map.get("message"));
        return map;
    }
}
