package com.cloud.pay.mapper;

import com.cloud.pay.entity.ThinkEquipmentMessage;
import com.cloud.pay.entity.ThinkEquipmentVideo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */

@Repository
public interface ThinkEquipmentVideoMapper {
    List<ThinkEquipmentVideo> findAll(ThinkEquipmentVideo equipmentVideo);

    int count(ThinkEquipmentVideo equipmentVideo);

    int insert(ThinkEquipmentVideo equipmentVideo);

    int delete(ThinkEquipmentVideo equipmentVideo);

    int update(ThinkEquipmentVideo equipmentVideo);

    ThinkEquipmentVideo findById(Integer id);

}
