package com.cloud.pay.service;

import com.cloud.pay.entity.ThinkEquipmentCate;
import com.cloud.pay.entity.ThinkEquipmentMessage;

import java.util.List;

/**
 * @Author:宁志洋
 * @Date:2020/9/16 20:33
 */

public interface ThinkEquipmentMessageService {
    List<ThinkEquipmentMessage> findByPage(ThinkEquipmentMessage equipmentMessage, Integer page, Integer limit);

    int count(ThinkEquipmentMessage equipmentMessage);

    int insert(ThinkEquipmentMessage equipmentMessage);

    int delete(ThinkEquipmentMessage equipmentMessage);

    int update(ThinkEquipmentMessage equipmentMessage);

    ThinkEquipmentMessage findById(Integer lId);

    ThinkEquipmentMessage findByEqId(Integer id);

    List<ThinkEquipmentMessage> findAll(ThinkEquipmentMessage thinkEquipmentMessage);
}
