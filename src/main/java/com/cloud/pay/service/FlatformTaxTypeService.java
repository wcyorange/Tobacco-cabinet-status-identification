package com.cloud.pay.service;

import com.cloud.pay.entity.FlatformTaxType;

import java.util.List;

public interface FlatformTaxTypeService {

    int insert(FlatformTaxType flatformTaxType);

    int update(FlatformTaxType flatformTaxType);

    int delete(Integer id);

    List<FlatformTaxType> findAll(FlatformTaxType flatformTaxType);

    int count(FlatformTaxType flatformTaxType);

    FlatformTaxType findById(Integer id);

    List<FlatformTaxType> findall(FlatformTaxType flatformTaxType, Integer page, Integer size);

    FlatformTaxType findByType(int i);
}
