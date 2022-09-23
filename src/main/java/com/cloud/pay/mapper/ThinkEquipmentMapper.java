package com.cloud.pay.mapper;

import com.cloud.pay.entity.Live;
import com.cloud.pay.entity.ThinkEquipment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */

@Repository
public interface ThinkEquipmentMapper {
    List<ThinkEquipment> findAll(ThinkEquipment equipment);

    int count(ThinkEquipment equipment);

    int insert(ThinkEquipment equipment);

    int delete(ThinkEquipment equipment);

    int update(ThinkEquipment equipment);

    ThinkEquipment findById(Integer id);

    ThinkEquipment findOneByName(String name);

    List<ThinkEquipment> findAllByCate(Integer cate);

    int updateStatus(ThinkEquipment equipment);

    int delAbNormalStatus(ThinkEquipment thinkEquipment);

    ThinkEquipment findOneByCode(String code);

    void updateMessageByCode(ThinkEquipment equipment);

    void updateLiveStatusByCode(ThinkEquipment equipment);

    String findCodeById(Integer cid);

    void updateStatusByCode(ThinkEquipment thinkEquipment1);
}
