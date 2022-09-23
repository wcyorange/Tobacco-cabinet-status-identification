package com.cloud.pay.mapper;

import com.cloud.pay.entity.FlatformCostFlow;

import java.util.List;

public interface FlatformCostFlowMapper {

    int insert(FlatformCostFlow flatformCostFlow);

    int update(FlatformCostFlow flatformCostFlow);

    int delte(Integer id);

    List<FlatformCostFlow> findAll(FlatformCostFlow flatformCostFlow);

    int count(FlatformCostFlow flatformCostFlow);

    FlatformCostFlow findById(Integer id);

    List<FlatformCostFlow> findByParId(Integer parid);

    Integer countByParId(Integer parId);
}
