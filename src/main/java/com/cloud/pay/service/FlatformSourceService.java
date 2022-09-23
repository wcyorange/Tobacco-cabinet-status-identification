package com.cloud.pay.service;

import com.cloud.pay.entity.FlatformSource;

import java.util.List;

public interface FlatformSourceService {

    int insert(FlatformSource flatformSource);

    int update(FlatformSource flatformSource);

    int delete(Integer id);

    List<FlatformSource> findAll(FlatformSource flatformSource);

    int count(FlatformSource flatformSource);

    FlatformSource findById(Integer id);

    List<FlatformSource> findall(FlatformSource flatformSource, Integer page, Integer size);
}
