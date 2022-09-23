package com.cloud.pay.serviceImpl;

import java.util.List;

import com.cloud.pay.entity.Threelevel;
import com.cloud.pay.mapper.ThreelevelMapper;
import com.cloud.pay.service.ThreelevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ThreelevelServiceImpl implements ThreelevelService {
	@Autowired
	private ThreelevelMapper threelevelMapper;

	@Override
	public List<Threelevel> findall() {
		// TODO Auto-generated method stub
		return threelevelMapper.findall();


	}

	@Override
	public List<Threelevel> findParentId(Integer id) {
		// TODO Auto-generated method stub
		return threelevelMapper.findParentId(id);
	}

}
