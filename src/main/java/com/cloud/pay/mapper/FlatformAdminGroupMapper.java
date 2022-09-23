package com.cloud.pay.mapper;

import com.cloud.pay.entity.FlatformAccount;
import com.cloud.pay.entity.FlatformAdminGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*@Descritpion:角色模块Mapper
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/31
**/
@Repository
public interface FlatformAdminGroupMapper {

    FlatformAdminGroup findByUid(@Param("uid") Integer uid);

    List<FlatformAdminGroup> findAll(FlatformAdminGroup flatformAdminGroup);

    int count(FlatformAdminGroup flatformAdminGroup);

    int insert(FlatformAdminGroup flatformAdminGroup);

    int update(FlatformAdminGroup flatformAdminGroup);

    int delete(FlatformAdminGroup flatformAdminGroup);

    FlatformAdminGroup findById(Integer id);


}
