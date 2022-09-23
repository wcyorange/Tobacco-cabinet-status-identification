package com.cloud.pay.mapper;

import java.util.List;

import com.cloud.pay.entity.Threelevel;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreelevelMapper {

	public List<Threelevel> findall();
	
	public List<Threelevel> findParentId(Integer id);
	
	public Threelevel findById(Integer id);
}