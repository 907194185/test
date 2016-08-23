package com.ly.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ly.pojo.PublicPlatformInfo;

@Repository
public interface PublicPlatformInfoMapper {
	
	public PublicPlatformInfo findPPIById(int id);
	
	public List<PublicPlatformInfo> findAllPPI();

}
