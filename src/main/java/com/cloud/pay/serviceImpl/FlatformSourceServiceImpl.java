package com.cloud.pay.serviceImpl;


import com.cloud.pay.entity.FlatformSource;
import com.cloud.pay.mapper.FlatformSourceMapper;
import com.cloud.pay.service.FlatformSourceService;
import com.cloud.pay.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlatformSourceServiceImpl implements FlatformSourceService {

    @Autowired
    private FlatformSourceMapper flatformSourceMapper;

    @Override
    public int insert(FlatformSource flatformSource) {
        return flatformSourceMapper.insert(flatformSource);
    }

    @Override
    public int update(FlatformSource flatformSource) {
        return flatformSourceMapper.update(flatformSource);
    }

    @Override
    public int delete(Integer id) {
        return flatformSourceMapper.delete(id);
    }

    @Override
    public List<FlatformSource> findAll(FlatformSource flatformSource) {
        return flatformSourceMapper.findAll(flatformSource);
    }

    @Override
    public int count(FlatformSource flatformSource) {
        return flatformSourceMapper.count(flatformSource);
    }

    @Override
    public FlatformSource findById(Integer id) {
        return flatformSourceMapper.findById(id);
    }

    @Override
    public List<FlatformSource> findall(FlatformSource flatformSource, Integer page, Integer size) {
        if(page==null){
        page = 1;
    }
        if(size == null){
        size = 10;
    }
    List<FlatformSource> flatformSources=new ArrayList<FlatformSource>();
        PageHelper.startPage(page,size);//PageHelper使用
        PageHelper.orderBy("s_id ASC");//分页排序
    flatformSources = flatformSourceMapper.findAll(flatformSource);
    int countNums = flatformSourceMapper.count(flatformSource);//总记录数
    PageBean<FlatformSource> pageBean = new PageBean<>(page, size, countNums);
        pageBean.setItems(flatformSources);
        return pageBean.getItems();
}
}
