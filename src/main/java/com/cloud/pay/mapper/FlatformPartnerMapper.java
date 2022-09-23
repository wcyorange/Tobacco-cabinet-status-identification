package com.cloud.pay.mapper;

import com.cloud.pay.entity.FlatformPartner;

import java.util.List;

public interface FlatformPartnerMapper {

    List<FlatformPartner> findAll(FlatformPartner flatformPartner);

    int count(FlatformPartner flatformPartner);

    int insert(FlatformPartner flatformPartner);

    int update(FlatformPartner flatformPartner);

    int delete(Integer id);

    FlatformPartner findById(Integer id);

    FlatformPartner findByOpenId(String openid);

    FlatformPartner findByPhone(String phone);
}
