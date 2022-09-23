package com.cloud.pay.serviceImpl;

import com.cloud.pay.config.TargetDataSource;
import com.cloud.pay.entity.FlatformAdminGroup;
import com.cloud.pay.entity.FlatformAdminMenu;
import com.cloud.pay.mapper.FlatformAdminGroupMapper;
import com.cloud.pay.mapper.FlatformAdminMenuMapper;
import com.cloud.pay.service.FlatformAdminMenuService;
import com.cloud.pay.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FlatformAdminMenuServiceImpl implements FlatformAdminMenuService {

    @Autowired
    private FlatformAdminMenuMapper flatformAdminMenuMapper;
    @Autowired
    private FlatformAdminGroupMapper flatformAdminGroupMapper;

    @Override
    @TargetDataSource("one")
    public List<FlatformAdminMenu> findByUid(Integer uid) {

        List<FlatformAdminMenu> flatformAdminMenus = new ArrayList<>();
        FlatformAdminGroup flatformAdminGroup = flatformAdminGroupMapper.findById(uid);
        List<String> result = Arrays.asList(flatformAdminGroup.getAgAuth().split(","));
        if(result!=null&&result.size()>0) {
            for (int i = 0; i < result.size(); i++) {
                flatformAdminMenus.add(flatformAdminMenuMapper.findById(Integer.parseInt(result.get(i))));
            }
        }
        return flatformAdminMenus;

    }

    @Override
    @TargetDataSource("one")
    public List<FlatformAdminMenu> findAll() {
        return flatformAdminMenuMapper.findAll();
    }

    @Override
    @TargetDataSource("one")
    public List<FlatformAdminMenu> findAllPage(FlatformAdminMenu flatformAdminMenu) {
        return flatformAdminMenuMapper.findAllPage(flatformAdminMenu);
    }

    @Override
    @TargetDataSource("one")
    public int count(FlatformAdminMenu flatformAdminMenu) {
        return flatformAdminMenuMapper.count(flatformAdminMenu);
    }

    @Override
    @TargetDataSource("one")
    public FlatformAdminMenu findById(Integer id) {
        return flatformAdminMenuMapper.findById(id);
    }

    @Override
    @TargetDataSource("one")
    public int insert(FlatformAdminMenu flatformAdminMenu) {
        return flatformAdminMenuMapper.insert(flatformAdminMenu);
    }

    @Override
    @TargetDataSource("one")
    public int update(FlatformAdminMenu flatformAdminMenu) {
        return flatformAdminMenuMapper.update(flatformAdminMenu);
    }

    @Override
    @TargetDataSource("one")
    public int delete(FlatformAdminMenu flatformAdminMenu) {
        return flatformAdminMenuMapper.delete(flatformAdminMenu);
    }
    /**
     *@Descritpion:分页查询,flatformAdminMenu为查询条件
     *@Param:[flatformAccount, currentPage, pageSize]
     *@return:java.util.List<com.cloud.pay.entity.FlatformAccount>
     *@Author:丁宁
     *@Data:2019/7/31
     **/
    @Override
    @TargetDataSource("one")
    public List<FlatformAdminMenu> findByPage(FlatformAdminMenu flatformAdminMenu, int currentPage, int pageSize) {PageHelper.startPage(currentPage,pageSize);//PageHelper使用
        List<FlatformAdminMenu> flatformAdminMenus = flatformAdminMenuMapper.findAllPage(flatformAdminMenu);       //全部记录列表
        int countNums = flatformAdminMenuMapper.count(flatformAdminMenu);         //总记录数
        PageBean<FlatformAdminMenu> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(flatformAdminMenus);
        return pageData.getItems();
    }

    /**
    *@Descritpion:根据pid查询上级菜单
    *@Param:[pid]
    *@return:com.cloud.pay.entity.FlatformAdminMenu
    *@Author:丁宁
    *@Data:2019/8/7
    **/
    @Override
    @TargetDataSource("one")
    public FlatformAdminMenu findByPid(Integer pid) {
        return flatformAdminMenuMapper.findById(pid);
    }

    /**
    *@Descritpion:根据等级查找等级
    *@Param:[value]
    *@return:java.util.List<com.cloud.pay.entity.FlatformAdminMenu>
    *@Author:丁宁
    *@Data:2019/8/26
    **/
    @Override
    @TargetDataSource("one")
    public List<FlatformAdminMenu> findByMenu(Integer value) {
        return flatformAdminMenuMapper.findByAuLevel(value);
    }

    /**
    *@Descritpion:根据类型type查找菜单
    *@Param:[type]
    *@return:java.util.List<com.cloud.pay.entity.FlatformAdminMenu>
    *@Author:丁宁
    *@Data:2019/8/26
    **/
    @Override
    @TargetDataSource("one")
    public List<FlatformAdminMenu> findByType(String type) {
        return flatformAdminMenuMapper.findByType(type);
    }


}
