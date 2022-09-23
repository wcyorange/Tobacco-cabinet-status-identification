package com.cloud.pay.service;

import com.cloud.pay.entity.ThinkEquipmentMessage;
import com.cloud.pay.entity.ThinkEquipmentVideo;

import java.util.List;

/**
 * @Author:宁志洋
 * @Date:2020/9/16 20:33
 */

public interface ThinkEquipmentVideoService {
    List<ThinkEquipmentVideo> findByPage(ThinkEquipmentVideo equipmentVideo, Integer page, Integer limit);

    int count(ThinkEquipmentVideo equipmentVideo);

    int insert(ThinkEquipmentVideo equipmentVideo);

    int delete(ThinkEquipmentVideo equipmentVideo);

    int update(ThinkEquipmentVideo equipmentVideo);

    ThinkEquipmentVideo findById(Integer id);
}
