package com.cloud.pay.serviceImpl;

import com.cloud.pay.entity.ThinkEquipment;
import com.cloud.pay.mapper.ThinkEquipmentMapper;
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
public class ThinkEquipmentServiceImpl implements ThinkEquipmentService {
    @Autowired
    ThinkEquipmentMapper thinkEquipmentMapper;


    @Override
    public List<ThinkEquipment> findByPage(ThinkEquipment equipment, Integer page, Integer limit) {
        if(page==null){
            page = 1;
        }
        if(limit == null){
            limit = 10;
        }
        PageHelper.startPage(page,limit);//PageHelper使用
        PageHelper.orderBy("id DESC");//分页排序
        List<ThinkEquipment> spt_userList = thinkEquipmentMapper.findAll(equipment);       //全部记录列表
        int countNums = thinkEquipmentMapper.count(equipment);         //总记录数
        PageBean<ThinkEquipment> pageData = new PageBean<>(page, limit, countNums);
        pageData.setItems(spt_userList);
        return pageData.getItems();
    }

    @Override
    public int count(ThinkEquipment equipment) {
        return thinkEquipmentMapper.count(equipment);
    }

    @Override
    public int insert(ThinkEquipment equipment) {
        return thinkEquipmentMapper.insert(equipment);
    }

    @Override
    public int delete(ThinkEquipment equipment) {
        return thinkEquipmentMapper.delete(equipment);
    }

    @Override
    public int update(ThinkEquipment equipment) {
        return thinkEquipmentMapper.update(equipment);
    }

    @Override
    public ThinkEquipment findById(Integer id) {
        return thinkEquipmentMapper.findById(id);
    }

    @Override
    public ThinkEquipment findOneByName(String name) {
        return thinkEquipmentMapper.findOneByName(name);
    }

    @Override
    public List<ThinkEquipment> findAllByCate(Integer cate) {
        return thinkEquipmentMapper.findAllByCate(cate);
    }

    @Override
    public List<ThinkEquipment> findAll(ThinkEquipment equipment) {
        return thinkEquipmentMapper.findAll(equipment);
    }

    @Override
    public int updateStatus(ThinkEquipment equipment) {
        return thinkEquipmentMapper.updateStatus(equipment);
    }

    @Override
    public int delAbNormalStatus(ThinkEquipment thinkEquipment) {
        return thinkEquipmentMapper.delAbNormalStatus(thinkEquipment);
    }

    @Override
    public ThinkEquipment findOneByCode(String code) {
        return thinkEquipmentMapper.findOneByCode(code);
    }

    @Override
    public void updateMessageByCode(ThinkEquipment equipment) {
        thinkEquipmentMapper.updateMessageByCode(equipment);
    }

    @Override
    public void updateLiveStatusByCode(ThinkEquipment equipment) {
        thinkEquipmentMapper.updateLiveStatusByCode(equipment);
    }

    @Override
    public String findCodeById(Integer Cid) {
        return thinkEquipmentMapper.findCodeById(Cid);
    }

    @Override
    public void updateStatusByCode(ThinkEquipment thinkEquipment1) {

        thinkEquipmentMapper.updateStatusByCode(thinkEquipment1);
    }

    @Override
    public Boolean haveCode(String code) {
        ThinkEquipment thinkEquipment = thinkEquipmentMapper.findOneByCode(code);
        if (thinkEquipment != null){
            return true;
        }
        return false;
    }
}
