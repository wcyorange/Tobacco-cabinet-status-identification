package com.cloud.pay.serviceImpl;

import com.cloud.pay.config.TargetDataSource;
import com.cloud.pay.entity.FlatformAccount;
import com.cloud.pay.mapper.FlatformAccountMapper;
import com.cloud.pay.service.FlatformAccountService;
import com.cloud.pay.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlatformAccountServiceImpl implements FlatformAccountService {

    @Autowired
    private FlatformAccountMapper flatformAccountMapper;

    @Override
    @TargetDataSource("one")
    public FlatformAccount findByAccount(String account) {
        return flatformAccountMapper.findByAccount(account);
    }

    @Override
    @TargetDataSource("one")
    public int insert(FlatformAccount flatformAccount) {
        return flatformAccountMapper.insert(flatformAccount);
    }

    @Override
    @TargetDataSource("one")
    public int update(FlatformAccount flatformAccount) {
        return flatformAccountMapper.update(flatformAccount);
    }

    @Override
    @TargetDataSource("one")
    public int delete(FlatformAccount flatformAccount) {
        return flatformAccountMapper.delete(flatformAccount);
    }
    /**
    *@Descritpion:分页查询,FlatformAccount为查询条件
    *@Param:[flatformAccount, currentPage, pageSize]
    *@return:java.util.List<com.cloud.pay.entity.FlatformAccount>
    *@Author:丁宁
    *@Data:2019/7/30
    **/
    @Override
    @TargetDataSource("one")
    public List<FlatformAccount> findByPage(FlatformAccount flatformAccount,Integer currentPage, Integer pageSize) {
        if(currentPage==null){
            currentPage = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        PageHelper.startPage(currentPage,pageSize);//PageHelper使用
        PageHelper.orderBy("u_id DESC");//分页排序
        List<FlatformAccount> flatformAccountList = flatformAccountMapper.findAll(flatformAccount);       //全部记录列表
        int countNums = flatformAccountMapper.count(flatformAccount);         //总记录数
        PageBean<FlatformAccount> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(flatformAccountList);
        return pageData.getItems();
    }

    @Override
    @TargetDataSource("one")
    public FlatformAccount findById(Integer id) {
        return flatformAccountMapper.findById(id);
    }

    @Override
    @TargetDataSource("one")
    public int count(FlatformAccount flatformAccount) {
        return flatformAccountMapper.count(flatformAccount);
    }

    @Override
    public List<FlatformAccount> findAll(FlatformAccount flatformAccount) {
        return flatformAccountMapper.findAll(flatformAccount);
    }

    @Override
    public FlatformAccount findByAgentId(Integer agentId) {
        return flatformAccountMapper.findByAgentId(agentId);
    }


}
