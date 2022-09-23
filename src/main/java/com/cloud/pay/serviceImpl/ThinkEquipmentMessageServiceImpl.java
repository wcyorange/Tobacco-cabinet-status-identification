package com.cloud.pay.serviceImpl;

import com.cloud.pay.entity.ThinkEquipmentCate;
import com.cloud.pay.entity.ThinkEquipmentMessage;
import com.cloud.pay.mapper.ThinkEquipmentCateMapper;
import com.cloud.pay.mapper.ThinkEquipmentMessageMapper;
import com.cloud.pay.service.ThinkEquipmentCateService;
import com.cloud.pay.service.ThinkEquipmentMessageService;
import com.cloud.pay.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:宁志洋
 * @Date:2020/9/16 20:34
 */
@Service
public class ThinkEquipmentMessageServiceImpl implements ThinkEquipmentMessageService {
    @Autowired
    ThinkEquipmentMessageMapper thinkEquipmentMessageMapper;


    @Override
    public List<ThinkEquipmentMessage> findByPage(ThinkEquipmentMessage equipmentMessage, Integer page, Integer limit) {
        if(page==null){
            page = 1;
        }
        if(limit == null){
            limit = 10;
        }
        PageHelper.startPage(page,limit);//PageHelper使用
        PageHelper.orderBy("id DESC");//分页排序
        List<ThinkEquipmentMessage> spt_userList = thinkEquipmentMessageMapper.findAll(equipmentMessage);       //全部记录列表
        int countNums = thinkEquipmentMessageMapper.count(equipmentMessage);         //总记录数
        PageBean<ThinkEquipmentMessage> pageData = new PageBean<>(page, limit, countNums);
        pageData.setItems(spt_userList);
        return pageData.getItems();
    }

    @Override
    public int count(ThinkEquipmentMessage equipmentMessage) {
        return thinkEquipmentMessageMapper.count(equipmentMessage);
    }

    @Override
    public int insert(ThinkEquipmentMessage equipmentMessage) {
        return thinkEquipmentMessageMapper.insert(equipmentMessage);
    }

    @Override
    public int delete(ThinkEquipmentMessage equipmentMessage) {
        return thinkEquipmentMessageMapper.delete(equipmentMessage);
    }

    @Override
    public int update(ThinkEquipmentMessage equipmentMessage) {
        return thinkEquipmentMessageMapper.update(equipmentMessage);
    }

    @Override
    public ThinkEquipmentMessage findById(Integer id) {
        return thinkEquipmentMessageMapper.findById(id);
    }

    @Override
    public ThinkEquipmentMessage findByEqId(Integer eqId) {
        return thinkEquipmentMessageMapper.findByEqId(eqId);
    }

    @Override
    public List<ThinkEquipmentMessage> findAll(ThinkEquipmentMessage thinkEquipmentMessage) {
        return thinkEquipmentMessageMapper.findAll(thinkEquipmentMessage);
    }
}
