package com.cloud.pay.serviceImpl;

import com.cloud.pay.entity.FlatformOrder;
import com.cloud.pay.mapper.FlatformOrderMapper;
import com.cloud.pay.service.FlatformOrderService;
import com.cloud.pay.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlatformOrderServiceImpl implements FlatformOrderService {
    @Autowired
    private FlatformOrderMapper flatformOrderMapper;


    @Override
    public Integer countByParId(Integer parId) {
        return flatformOrderMapper.countByParId(parId);
    }

    @Override
    public List<FlatformOrder> findall(FlatformOrder flatformOrder, Integer page, Integer limit) {
        if(page==null){
            page = 1;
        }
        if(limit == null){
            limit = 10;
        }
        List<FlatformOrder> flatformOrders = flatformOrderMapper.findAll(flatformOrder);       //全部记录列表
        PageHelper.startPage(page,limit);//PageHelper使用
        PageHelper.orderBy("order_id DESC");//分页排序
        int countNums = flatformOrderMapper.count(flatformOrder);         //总记录数
        PageBean<FlatformOrder> pageData = new PageBean<>(page, limit, countNums);
        pageData.setItems(flatformOrders);
        return pageData.getItems();
    }

    @Override
    public int count(FlatformOrder flatformOrder) {
        return flatformOrderMapper.count(flatformOrder);
    }

    /**
     * 添加订单
     * @param flatformOrder
     * @return
     */
    @Override
    public int insertOrder(FlatformOrder flatformOrder) {
        return flatformOrderMapper.insertOrder(flatformOrder);
    }
}
