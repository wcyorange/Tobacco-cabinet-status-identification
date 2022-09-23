package com.cloud.pay.mapper;

import com.cloud.pay.entity.FlatformAccount;
import com.cloud.pay.entity.FlatformAdminGroup;
import com.cloud.pay.entity.FlatformAdminMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
*@Descritpion:菜单模块Mapper
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/31
**/
@Repository
public interface FlatformAdminMenuMapper {

    FlatformAdminMenu findByUid(@Param("uid") Integer uid);

    List<FlatformAdminMenu> findAll();

    List<FlatformAdminMenu> findAllPage(FlatformAdminMenu flatformAdminMenu);

    int count(FlatformAdminMenu flatformAdminMenu);

    int insert(FlatformAdminMenu flatformAdminMenu);

    int update(FlatformAdminMenu flatformAdminMenu);

    int delete(FlatformAdminMenu flatformAdminMenu);

    FlatformAdminMenu findById(@Param("id") Integer id);

    List<FlatformAdminMenu> findByAuLevel(@Param("level")Integer level);

    List<FlatformAdminMenu> findByType(String type);
}
