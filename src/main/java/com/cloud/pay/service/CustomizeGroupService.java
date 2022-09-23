package com.cloud.pay.service;

import com.cloud.pay.entity.CustomizeGroup;

import java.util.List;

/**
 * @Author:宁志洋
 * @Date:2020/9/16 20:33
 */

public interface CustomizeGroupService {
    List<CustomizeGroup> findByPage(CustomizeGroup customizeGroup, Integer page, Integer limit);

    int count(CustomizeGroup customizeGroup);

    int insert(CustomizeGroup customizeGroup);

    int delete(CustomizeGroup customizeGroup);

    int update(CustomizeGroup customizeGroup);

    CustomizeGroup findById(Integer lId);

    List<CustomizeGroup> findByuName(CustomizeGroup customizeGroup, Integer page, Integer limit);
}
