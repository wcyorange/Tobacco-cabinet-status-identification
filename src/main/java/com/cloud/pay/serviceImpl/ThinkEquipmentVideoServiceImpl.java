package com.cloud.pay.serviceImpl;

import com.cloud.pay.entity.ThinkEquipmentMessage;
import com.cloud.pay.entity.ThinkEquipmentVideo;
import com.cloud.pay.mapper.ThinkEquipmentMessageMapper;
import com.cloud.pay.mapper.ThinkEquipmentVideoMapper;
import com.cloud.pay.service.ThinkEquipmentMessageService;
import com.cloud.pay.service.ThinkEquipmentVideoService;
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
public class ThinkEquipmentVideoServiceImpl implements ThinkEquipmentVideoService {
    @Autowired
    ThinkEquipmentVideoMapper thinkEquipmentVideoMapper;


    @Override
    public List<ThinkEquipmentVideo> findByPage(ThinkEquipmentVideo equipmentVideo, Integer page, Integer limit) {
        if(page==null){
            page = 1;
        }
        if(limit == null){
            limit = 10;
        }
        PageHelper.startPage(page,limit);//PageHelper使用
        PageHelper.orderBy("id DESC");//分页排序
        List<ThinkEquipmentVideo> spt_userList = thinkEquipmentVideoMapper.findAll(equipmentVideo);       //全部记录列表
        int countNums = thinkEquipmentVideoMapper.count(equipmentVideo);         //总记录数
        PageBean<ThinkEquipmentVideo> pageData = new PageBean<>(page, limit, countNums);
        pageData.setItems(spt_userList);
        return pageData.getItems();
    }

    @Override
    public int count(ThinkEquipmentVideo equipmentMessage) {
        return thinkEquipmentVideoMapper.count(equipmentMessage);
    }

    @Override
    public int insert(ThinkEquipmentVideo equipmentMessage) {
        return thinkEquipmentVideoMapper.insert(equipmentMessage);
    }

    @Override
    public int delete(ThinkEquipmentVideo equipmentMessage) {
        return thinkEquipmentVideoMapper.delete(equipmentMessage);
    }

    @Override
    public int update(ThinkEquipmentVideo equipmentMessage) {
        return thinkEquipmentVideoMapper.update(equipmentMessage);
    }

    @Override
    public ThinkEquipmentVideo findById(Integer id) {
        return thinkEquipmentVideoMapper.findById(id);
    }
}
