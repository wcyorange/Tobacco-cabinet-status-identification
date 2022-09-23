package com.cloud.pay.mapper;

import com.cloud.pay.entity.ThinkEquipmentCate;
import com.cloud.pay.entity.ThinkEquipmentMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */

@Repository
public interface ThinkEquipmentMessageMapper {
    List<ThinkEquipmentMessage> findAll(ThinkEquipmentMessage equipmentMessage);

    int count(ThinkEquipmentMessage equipmentMessage);

    int insert(ThinkEquipmentMessage equipmentMessage);

    int delete(ThinkEquipmentMessage equipmentMessage);

    int update(ThinkEquipmentMessage equipmentMessage);

    ThinkEquipmentMessage findById(Integer id);

    ThinkEquipmentMessage findByEqId(Integer eqId);
}
