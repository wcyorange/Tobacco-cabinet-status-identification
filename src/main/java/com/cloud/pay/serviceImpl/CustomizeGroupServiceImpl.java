package com.cloud.pay.serviceImpl;

import com.cloud.pay.entity.CustomizeGroup;
import com.cloud.pay.mapper.CustomizeGroupMapper;
import com.cloud.pay.service.CustomizeGroupService;
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
public class CustomizeGroupServiceImpl implements CustomizeGroupService {
    @Autowired
    CustomizeGroupMapper customizeGroupMapper;


    @Override
    public List<CustomizeGroup> findByPage(CustomizeGroup customizeGroup, Integer page, Integer limit) {
        if(page==null){
            page = 1;
        }
        if(limit == null){
            limit = 10;
        }
        PageHelper.startPage(page,limit);//PageHelper使用
        PageHelper.orderBy("id DESC");//分页排序
        List<CustomizeGroup> spt_userList = customizeGroupMapper.findAll(customizeGroup);       //全部记录列表
        int countNums = customizeGroupMapper.count(customizeGroup);         //总记录数
        PageBean<CustomizeGroup> pageData = new PageBean<>(page, limit, countNums);
        pageData.setItems(spt_userList);
        return pageData.getItems();
    }

    @Override
    public int count(CustomizeGroup customizeGroup) {
        return customizeGroupMapper.count(customizeGroup);
    }

    @Override
    public int insert(CustomizeGroup customizeGroup) {
        return customizeGroupMapper.insert(customizeGroup);
    }

    @Override
    public int delete(CustomizeGroup customizeGroup) {
        return customizeGroupMapper.delete(customizeGroup);
    }

    @Override
    public int update(CustomizeGroup customizeGroup) {
        return customizeGroupMapper.update(customizeGroup);
    }

    @Override
    public CustomizeGroup findById(Integer id) {
        return customizeGroupMapper.findById(id);
    }

    @Override
    public List<CustomizeGroup> findByuName(CustomizeGroup customizeGroup, Integer page, Integer limit) {
        if(page==null){
            page = 1;
        }
        if(limit == null){
            limit = 10;
        }
        PageHelper.startPage(page,limit);//PageHelper使用
        PageHelper.orderBy("id DESC");//分页排序
        List<CustomizeGroup> spt_userList = customizeGroupMapper.findByuName(customizeGroup);       //全部记录列表
        int countNums = customizeGroupMapper.count(customizeGroup);         //总记录数
        PageBean<CustomizeGroup> pageData = new PageBean<>(page, limit, countNums);
        pageData.setItems(spt_userList);
        return pageData.getItems();
    }
}
