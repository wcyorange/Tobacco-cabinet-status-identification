package com.cloud.pay.serviceImpl;

import com.cloud.pay.entity.FlatformPartner;
import com.cloud.pay.mapper.FlatformPartnerMapper;
import com.cloud.pay.service.FlatformPartnerService;
import com.cloud.pay.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FlatformPartnerServiceImpl implements FlatformPartnerService {


    @Autowired
    private FlatformPartnerMapper flatformPartnerMapper;

    @Override
    public int insert(FlatformPartner flatformPartner) {
        return flatformPartnerMapper.insert(flatformPartner);
    }

    @Override
    public int update(FlatformPartner flatformPartner) {
        return flatformPartnerMapper.update(flatformPartner);
    }

    @Override
    public int delete(Integer id) {
        return flatformPartnerMapper.delete(id);
    }

    @Override
    public List<FlatformPartner> findAll(FlatformPartner flatformPartner) {
        return flatformPartnerMapper.findAll(flatformPartner);
    }

    @Override
    public int count(FlatformPartner flatformPartner) {
        return flatformPartnerMapper.count(flatformPartner);
    }

    @Override
    public FlatformPartner findById(Integer id) {
        return flatformPartnerMapper.findById(id);
    }

    @Override
    public List<FlatformPartner> findall(FlatformPartner partner, Integer currentPage, Integer pageSize) {
        if(currentPage==null){
            currentPage = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        List<FlatformPartner> flatformPartners = flatformPartnerMapper.findAll(partner);       //全部记录列表
        PageHelper.startPage(currentPage,pageSize);//PageHelper使用
        PageHelper.orderBy("par_id DESC");//分页排序

        int countNums = flatformPartnerMapper.count(partner);         //总记录数
        PageBean<FlatformPartner> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(flatformPartners);
        return pageData.getItems();
    }

    @Override
    public FlatformPartner findByOpenId(String openid) {
        return flatformPartnerMapper.findByOpenId(openid);
    }

    @Override
    public FlatformPartner findByPhone(String phone) {
        return flatformPartnerMapper.findByPhone(phone);
    }
}
