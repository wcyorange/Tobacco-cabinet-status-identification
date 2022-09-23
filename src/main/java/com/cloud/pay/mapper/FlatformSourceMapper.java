package com.cloud.pay.mapper;

import com.cloud.pay.entity.FlatformPartner;
import com.cloud.pay.entity.FlatformSource;

import java.util.List;

public interface FlatformSourceMapper {

    List<FlatformSource> findAll(FlatformSource flatformSource);

    int count(FlatformSource flatformSource);

    int insert(FlatformSource flatformSource);

    int update(FlatformSource flatformSource);

    int delete(Integer id);

    FlatformSource findById(Integer id);
}
