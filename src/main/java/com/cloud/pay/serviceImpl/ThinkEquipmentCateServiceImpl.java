package com.cloud.pay.serviceImpl;

import com.cloud.pay.entity.Camera;
import com.cloud.pay.entity.ThinkEquipment;
import com.cloud.pay.entity.ThinkEquipmentCate;
import com.cloud.pay.mapper.ThinkEquipmentCateMapper;
import com.cloud.pay.mapper.ThinkEquipmentMapper;
import com.cloud.pay.service.ThinkEquipmentCateService;
import com.cloud.pay.service.ThinkEquipmentService;
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
public class ThinkEquipmentCateServiceImpl implements ThinkEquipmentCateService {
    @Autowired
    ThinkEquipmentCateMapper thinkEquipmentCateMapper;


    @Override
    public List<ThinkEquipmentCate> findByPage(ThinkEquipmentCate equipmentCate, Integer page, Integer limit) {
        if(page==null){
            page = 1;
        }
        if(limit == null){
            limit = 10;
        }
        PageHelper.startPage(page,limit);//PageHelper使用
        PageHelper.orderBy("id DESC");//分页排序
        List<ThinkEquipmentCate> spt_userList = thinkEquipmentCateMapper.findAll(equipmentCate);       //全部记录列表
        int countNums = thinkEquipmentCateMapper.count(equipmentCate);         //总记录数
        PageBean<ThinkEquipmentCate> pageData = new PageBean<>(page, limit, countNums);
        pageData.setItems(spt_userList);
        return pageData.getItems();
    }

    @Override
    public int count(ThinkEquipmentCate equipmentCate) {
        return thinkEquipmentCateMapper.count(equipmentCate);
    }

    @Override
    public int insert(ThinkEquipmentCate equipmentCate) {
        return thinkEquipmentCateMapper.insert(equipmentCate);
    }

    @Override
    public int delete(ThinkEquipmentCate equipmentCate) {
        return thinkEquipmentCateMapper.delete(equipmentCate);
    }

    @Override
    public int update(ThinkEquipmentCate equipmentCate) {
        return thinkEquipmentCateMapper.update(equipmentCate);
    }

    @Override
    public ThinkEquipmentCate findById(Integer id) {
        return thinkEquipmentCateMapper.findById(id);
    }

    @Override
    public List<ThinkEquipmentCate> findAll(ThinkEquipmentCate equipmentCate) {
        return thinkEquipmentCateMapper.findAll(equipmentCate);
    }
}
