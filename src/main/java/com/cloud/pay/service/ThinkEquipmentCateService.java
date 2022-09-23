package com.cloud.pay.service;

import com.cloud.pay.entity.Camera;
import com.cloud.pay.entity.ThinkEquipment;
import com.cloud.pay.entity.ThinkEquipmentCate;

import java.util.List;

/**
 * @Author:宁志洋
 * @Date:2020/9/16 20:33
 */

public interface ThinkEquipmentCateService {
    List<ThinkEquipmentCate> findByPage(ThinkEquipmentCate equipmentCate, Integer page, Integer limit);

    int count(ThinkEquipmentCate equipmentCate);

    int insert(ThinkEquipmentCate equipmentCate);

    int delete(ThinkEquipmentCate equipmentCate);

    int update(ThinkEquipmentCate equipmentCate);

    ThinkEquipmentCate findById(Integer lId);

    List<ThinkEquipmentCate> findAll(ThinkEquipmentCate equipmentCate);
}
