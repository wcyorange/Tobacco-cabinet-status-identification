package com.cloud.pay.mapper;

import com.cloud.pay.entity.FlatformTaxType;

import java.util.List;

public interface FlatformTaxTypeMapper {


    int insert(FlatformTaxType flatformTaxTypeMapper);

    int update(FlatformTaxType flatformTaxTypeMapper);

    List<FlatformTaxType> findAll(FlatformTaxType flatformTaxTypeMapper);

    int count(FlatformTaxType flatformTaxTypeMapper);

    FlatformTaxType findById(Integer id);

    int delete(Integer id);

    FlatformTaxType findByType(int i);
}
