package com.cloud.pay.mapper;

import com.cloud.pay.entity.FlatformAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*@Descritpion: 账号Mapper
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/30
**/
@Repository
public interface FlatformAccountMapper {

    FlatformAccount findByAccount(@Param("account") String account);

    int insert(FlatformAccount flatformAccount);

    int update(FlatformAccount flatformAccount);

    int delete(FlatformAccount flatformAccount);

    List<FlatformAccount> findAll(FlatformAccount flatformAccount);

    int count(FlatformAccount flatformAccount);

    FlatformAccount findById(Integer id);

    FlatformAccount findByAgentId(Integer agentId);
}
