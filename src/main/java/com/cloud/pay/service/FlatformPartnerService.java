package com.cloud.pay.service;

import com.cloud.pay.entity.FlatformPartner;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface FlatformPartnerService {

    int insert(FlatformPartner flatformPartner);

    int update(FlatformPartner flatformPartner);

    int delete(Integer id);

    List<FlatformPartner> findAll(FlatformPartner flatformPartner);

    int count(FlatformPartner flatformPartner);

    FlatformPartner findById(Integer id);

    List<FlatformPartner> findall(FlatformPartner partner, Integer page, Integer limit);

    FlatformPartner findByOpenId(String openid);

    FlatformPartner findByPhone(String phone);
}
