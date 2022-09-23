package com.cloud.pay.service;

import com.cloud.pay.entity.FlatformAccount;

import java.util.List;

/**
*@Descritpion: 账号service
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/30
**/
public interface FlatformAccountService {

    FlatformAccount findByAccount(String account);

    int insert(FlatformAccount flatformAccount);

    int update(FlatformAccount flatformAccount);

    int delete(FlatformAccount flatformAccount);

    List<FlatformAccount> findByPage(FlatformAccount flatformAccount,Integer currentPage,Integer pageSize);

    FlatformAccount findById(Integer id);

    int count(FlatformAccount flatformAccount);

    List<FlatformAccount> findAll(FlatformAccount flatformAccount);

    FlatformAccount findByAgentId(Integer agentId);
}
