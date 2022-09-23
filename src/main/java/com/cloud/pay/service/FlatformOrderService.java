package com.cloud.pay.service;

import com.cloud.pay.entity.FlatformOrder;

import java.util.List;

public interface FlatformOrderService {
    Integer countByParId(Integer parId);

    List<FlatformOrder> findall(FlatformOrder flatformOrder, Integer page, Integer limit);

    int count(FlatformOrder flatformOrder);

    int insertOrder(FlatformOrder flatformOrder);
}
