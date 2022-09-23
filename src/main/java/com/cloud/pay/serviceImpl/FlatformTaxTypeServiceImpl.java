package com.cloud.pay.serviceImpl;

import com.cloud.pay.entity.FlatformSource;
import com.cloud.pay.entity.FlatformTaxType;
import com.cloud.pay.mapper.FlatformTaxTypeMapper;
import com.cloud.pay.service.FlatformSourceService;
import com.cloud.pay.service.FlatformTaxTypeService;
import com.cloud.pay.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlatformTaxTypeServiceImpl implements FlatformTaxTypeService {

    @Autowired
    private FlatformTaxTypeMapper flatformTaxTypeMapper;

    @Override
    public int insert(FlatformTaxType flatformTaxType) {
        return flatformTaxTypeMapper.insert(flatformTaxType);
    }

    @Override
    public int update(FlatformTaxType flatformTaxType) {
        return flatformTaxTypeMapper.update(flatformTaxType);
    }

    @Override
    public int delete(Integer id) {
        return flatformTaxTypeMapper.delete(id);
    }

    @Override
    public List<FlatformTaxType> findAll(FlatformTaxType flatformTaxType) {
        return flatformTaxTypeMapper.findAll(flatformTaxType);
    }

    @Override
    public int count(FlatformTaxType flatformTaxType) {
        return flatformTaxTypeMapper.count(flatformTaxType);
    }

    @Override
    public FlatformTaxType findById(Integer id) {
        return flatformTaxTypeMapper.findById(id);
    }

    @Override
    public List<FlatformTaxType> findall(FlatformTaxType flatformTaxType, Integer page, Integer size) {
        if(page==null){
            page = 1;
        }
        if(size == null){
            size = 10;
        }
        List<FlatformTaxType> flatformTaxTypes=new ArrayList<FlatformTaxType>();
        PageHelper.startPage(page,size);//PageHelper使用
        PageHelper.orderBy("tt_id ASC");//分页排序
        flatformTaxTypes = flatformTaxTypeMapper.findAll(flatformTaxType);
        int countNums = flatformTaxTypeMapper.count(flatformTaxType);//总记录数
        PageBean<FlatformTaxType> pageBean = new PageBean<>(page, size, countNums);
        pageBean.setItems(flatformTaxTypes);
        return pageBean.getItems();
    }

    /**
     * 根据消费类型查找
     * @param i
     * @return
     */
    @Override
    public FlatformTaxType findByType(int i) {
        return flatformTaxTypeMapper.findByType(i);
    }
}
