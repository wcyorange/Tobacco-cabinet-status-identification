package com.cloud.pay.mapper;

import com.cloud.pay.entity.FlatformOrder;

import java.util.List;

public interface FlatformOrderMapper {
    Integer countByParId(Integer parId);

    List<FlatformOrder> findAll(FlatformOrder flatformOrder);

    int count(FlatformOrder flatformOrder);

    int insertOrder(FlatformOrder flatformOrder);
}
