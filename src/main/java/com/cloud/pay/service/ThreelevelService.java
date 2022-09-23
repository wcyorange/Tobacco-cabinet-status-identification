package com.cloud.pay.service;

import java.util.List;

import com.cloud.pay.entity.Threelevel;

public interface ThreelevelService {

	public List<Threelevel> findall();
	
	public List<Threelevel> findParentId(Integer id);

}
