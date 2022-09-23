package com.cloud.pay.service;

import com.cloud.pay.entity.FlatformAccount;
import com.cloud.pay.entity.FlatformAdminGroup;

import java.util.List;

/**
*@Descritpion:角色模块service
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/31
**/
public interface FlatformAdminGroupService {

    FlatformAdminGroup findByUid(Integer uid);

    int insert(FlatformAdminGroup flatformAdminGroup);

    int update(FlatformAdminGroup flatformAdminGroup);

    int delete(FlatformAdminGroup flatformAdminGroup);

    FlatformAdminGroup findById(Integer id);

    List<FlatformAdminGroup> findByPage(FlatformAdminGroup flatformAdminGroup,int currentPage,int pageSize);

    List<FlatformAdminGroup> findAll(FlatformAdminGroup flatformAdminGroup);

    int count(FlatformAdminGroup flatformAdminGroup);
}
