package com.cloud.pay.serviceImpl;

import com.cloud.pay.config.TargetDataSource;
import com.cloud.pay.entity.FlatformAccount;
import com.cloud.pay.entity.FlatformAdminGroup;
import com.cloud.pay.mapper.FlatformAdminGroupMapper;
import com.cloud.pay.service.FlatformAdminGroupService;
import com.cloud.pay.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlatformAdminGroupServiceImpl implements FlatformAdminGroupService {

    @Autowired
    private FlatformAdminGroupMapper flatformAdminGroupMapper;

    @Override
    @TargetDataSource("one")
    public FlatformAdminGroup findByUid(Integer uid) {
        return flatformAdminGroupMapper.findByUid(uid);
    }

    @Override
    @TargetDataSource("one")
    public int insert(FlatformAdminGroup flatformAdminGroup) {
        return flatformAdminGroupMapper.insert(flatformAdminGroup);
    }

    @Override
    @TargetDataSource("one")
    public int update(FlatformAdminGroup flatformAdminGroup) {
        return flatformAdminGroupMapper.update(flatformAdminGroup);
    }

    @Override
    @TargetDataSource("one")
    public int delete(FlatformAdminGroup flatformAdminGroup) {
        return flatformAdminGroupMapper.delete(flatformAdminGroup);
    }

    @Override
    @TargetDataSource("one")
    public FlatformAdminGroup findById(Integer id) {
        return flatformAdminGroupMapper.findById(id);
    }
    /**
     *@Descritpion:分页查询,flatformAdminGroup为查询条件
     *@Param:[flatformAccount, currentPage, pageSize]
     *@return:java.util.List<com.cloud.pay.entity.FlatformAccount>
     *@Author:丁宁
     *@Data:2019/7/31
     **/
    @Override
    @TargetDataSource("one")
    public List<FlatformAdminGroup> findByPage(FlatformAdminGroup flatformAdminGroup, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);//PageHelper使用
        PageHelper.orderBy("ag_id DESC");//分页排序
        List<FlatformAdminGroup> flatformAccountgrouplist = flatformAdminGroupMapper.findAll(flatformAdminGroup);       //全部记录列表
        int countNums = flatformAdminGroupMapper.count(flatformAdminGroup);         //总记录数
        PageBean<FlatformAdminGroup> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(flatformAccountgrouplist);
        return pageData.getItems();
    }

    @Override
    @TargetDataSource("one")
    public List<FlatformAdminGroup> findAll(FlatformAdminGroup flatformAdminGroup) {
        return flatformAdminGroupMapper.findAll(flatformAdminGroup);
    }

    @Override
    @TargetDataSource("one")
    public int count(FlatformAdminGroup flatformAdminGroup) {
        return flatformAdminGroupMapper.count(flatformAdminGroup);
    }
}
