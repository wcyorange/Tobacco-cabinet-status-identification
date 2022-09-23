package com.cloud.pay.mapper;

import com.cloud.pay.entity.CustomizeGroup;
import com.cloud.pay.entity.ThinkEquipment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */

@Repository
public interface CustomizeGroupMapper {
    List<CustomizeGroup> findAll(CustomizeGroup customizeGroup);

    int count(CustomizeGroup customizeGroup);

    int insert(CustomizeGroup customizeGroup);

    int delete(CustomizeGroup customizeGroup);

    int update(CustomizeGroup customizeGroup);

    CustomizeGroup findById(Integer id);

    List<CustomizeGroup> findByuName(CustomizeGroup customizeGroup);
}
