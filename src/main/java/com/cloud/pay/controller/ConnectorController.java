package com.cloud.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.ThinkEquipment;
import com.cloud.pay.service.ThinkEquipmentService;
import com.cloud.pay.util.GetSingleStatusUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Author:宁志洋
 * @Date:2020/9/19 16:43
 */
@RestController
public class ConnectorController {

    @Resource
    private ThinkEquipmentService thinkEquipmentService;

    @RequestMapping("/getCameraCode")
    public JSONObject getCameraCode(String code){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(simpleDateFormat.format(new Date()));
        JSONObject json = new JSONObject();
        Boolean rs = thinkEquipmentService.haveCode(code);
        if (rs){
            ThinkEquipment thinkEquipment = thinkEquipmentService.findOneByCode(code);
            Map<String,String> map = GetSingleStatusUtil.getSingleStatus(thinkEquipment);
            json.put("code",code);
            json.put("status",map.get("status"));
            json.put("message",map.get("message"));
        }else {
            json.put("code",code);
            json.put("status",404);
            json.put("message","当前机柜组中无此柜号！！！");
        }
        return json;
    }

//    @RequestMapping("/getCameraGroup")
//    public JSONObject getCameraGroup(String code){
//        JSONObject json = new JSONObject();
//        return json;
//    }
}
