package com.cloud.pay.serviceImpl;

import com.cloud.pay.entity.FlatformCostFlow;
import com.cloud.pay.entity.FlatformSource;
import com.cloud.pay.mapper.FlatformCostFlowMapper;
import com.cloud.pay.service.FlatformCostFlowService;
import com.cloud.pay.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlatformCostFlowServiceImpl implements FlatformCostFlowService {

    @Autowired
    private FlatformCostFlowMapper flatformCostFlowMapper;

    @Override
    public int insert(FlatformCostFlow flatformCostFlow) {
        return flatformCostFlowMapper.insert(flatformCostFlow);
    }

    @Override
    public int update(FlatformCostFlow flatformCostFlow) {
        return flatformCostFlowMapper.update(flatformCostFlow);
    }

    @Override
    public int delete(Integer id) {
        return flatformCostFlowMapper.delte(id);
    }

    @Override
    public List<FlatformCostFlow> findAll(FlatformCostFlow flatformCostFlow) {
        return flatformCostFlowMapper.findAll(flatformCostFlow);
    }

    @Override
    public int count(FlatformCostFlow flatformCostFlow) {
        return flatformCostFlowMapper.count(flatformCostFlow);
    }

    @Override
    public FlatformCostFlow findById(Integer id) {
        return flatformCostFlowMapper.findById(id);
    }

    @Override
    public List<FlatformCostFlow> findall(FlatformCostFlow flatformCostFlow, Integer page, Integer size) {
        if(page==null){
            page = 1;
        }
        if(size == null){
            size = 10;
        }
        List<FlatformCostFlow> flatformCostFlows=new ArrayList<FlatformCostFlow>();
        PageHelper.startPage(page,size);//PageHelper使用
        PageHelper.orderBy("cf_id ASC");//分页排序
        flatformCostFlows = flatformCostFlowMapper.findAll(flatformCostFlow);
        int countNums = flatformCostFlowMapper.count(flatformCostFlow);//总记录数
        PageBean<FlatformCostFlow> pageBean = new PageBean<>(page, size, countNums);
        pageBean.setItems(flatformCostFlows);
        return pageBean.getItems();
    }

    /**
     * 根据parId统计数量
     * @param parId
     * @return
     */
    @Override
    public Integer countByParId(Integer parId) {
        return flatformCostFlowMapper.countByParId(parId);
    }
}
