package com.cloud.pay.util;

import com.cloud.pay.config.BeanContext;
import com.cloud.pay.entity.Camera;
import com.cloud.pay.entity.ThinkEquipmentMessage;
import com.cloud.pay.service.ThinkEquipmentMessageService;

import java.util.Arrays;
import java.util.List;

/**
 * @author 宁志洋
 * @date 2020/10/28
 */
public class PostThread implements Runnable {

    private ThinkEquipmentMessageService thinkEquipmentMessageService;
    private Integer id;
    private String abnormalName;
    private Camera camera;
    private ThinkEquipmentMessage equipmentMessage;

    public void init(Camera camera, Integer id, String abnormalName,ThinkEquipmentMessage equipmentMessage) {
        this.id = id;
        this.camera = camera;
        this.abnormalName = abnormalName;
        this.equipmentMessage = equipmentMessage;
    }

    @Override
    public void run() {
        this.thinkEquipmentMessageService = BeanContext.getApplicationContext().getBean(ThinkEquipmentMessageService.class);

        System.out.println("py 接口返回10005");
        List<String> lists = Arrays.asList((this.abnormalName).split(","));
        for (String s : lists) {
            FileMoveUtil.fileMove(s);
        }
        thinkEquipmentMessageService.insert(this.equipmentMessage);
        PreviewUtil previewUtil = new PreviewUtil();
        previewUtil.getHkwsCamera(this.camera, 1);


    }
}
