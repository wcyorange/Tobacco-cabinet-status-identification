package com.cloud.pay.service;

import com.cloud.pay.entity.Live;
import com.cloud.pay.entity.ThinkEquipment;

import java.util.List;

/**
 * @Author:宁志洋
 * @Date:2020/9/16 20:33
 */

public interface ThinkEquipmentService {
    List<ThinkEquipment> findByPage(ThinkEquipment equipment, Integer page, Integer limit);

    int count(ThinkEquipment equipment);

    int insert(ThinkEquipment equipment);

    int delete(ThinkEquipment equipment);

    int update(ThinkEquipment equipment);

    ThinkEquipment findById(Integer lId);

    ThinkEquipment findOneByName(String name);

    List<ThinkEquipment> findAllByCate(Integer cate);

    List<ThinkEquipment> findAll(ThinkEquipment equipment);

    int updateStatus(ThinkEquipment equipment);

    int delAbNormalStatus(ThinkEquipment thinkEquipment);

    ThinkEquipment findOneByCode(String code);


    void updateMessageByCode(ThinkEquipment equipment);

    void updateLiveStatusByCode(ThinkEquipment equipment);

    String findCodeById(Integer Cid);

    void  updateStatusByCode(ThinkEquipment thinkEquipment1);

    Boolean haveCode(String code);
}
