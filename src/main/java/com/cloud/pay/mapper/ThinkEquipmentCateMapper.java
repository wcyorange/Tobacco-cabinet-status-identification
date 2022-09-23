package com.cloud.pay.mapper;

import com.cloud.pay.entity.ThinkEquipment;
import com.cloud.pay.entity.ThinkEquipmentCate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */

@Repository
public interface ThinkEquipmentCateMapper {
    List<ThinkEquipmentCate> findAll(ThinkEquipmentCate equipmentCate);

    int count(ThinkEquipmentCate equipmentCate);

    int insert(ThinkEquipmentCate equipmentCate);

    int delete(ThinkEquipmentCate equipmentCate);

    int update(ThinkEquipmentCate equipmentCate);

    ThinkEquipmentCate findById(Integer id);

}
