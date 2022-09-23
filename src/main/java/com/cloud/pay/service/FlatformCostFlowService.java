package com.cloud.pay.service;

import com.cloud.pay.entity.FlatformCostFlow;

import java.util.List;

public interface FlatformCostFlowService {

    int insert(FlatformCostFlow flatformCostFlow);

    int update(FlatformCostFlow flatformCostFlow);

    int delete(Integer id);

    List<FlatformCostFlow> findAll(FlatformCostFlow flatformCostFlow);

    int count(FlatformCostFlow flatformCostFlow);

    FlatformCostFlow findById(Integer id);

    List<FlatformCostFlow> findall(FlatformCostFlow flatformCostFlow, Integer page, Integer size);

    Integer countByParId(Integer parId);
}
