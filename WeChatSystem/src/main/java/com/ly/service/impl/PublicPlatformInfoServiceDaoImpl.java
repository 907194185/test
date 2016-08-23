package com.ly.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.mapper.PublicPlatformInfoMapper;
import com.ly.pojo.PublicPlatformInfo;
import com.ly.service.dao.PublicPlatformInfoServiceDao;

/**
 * 公众号配置信息服务实现层
 * @author 刘勇
 *
 */
@Service
public class PublicPlatformInfoServiceDaoImpl implements PublicPlatformInfoServiceDao{
	
	@Autowired
	private PublicPlatformInfoMapper publicPlatformInfoMapper;
	
	
	@Override
	public PublicPlatformInfo getPPIById(int id) {
		PublicPlatformInfo platformInfo = publicPlatformInfoMapper.findPPIById(id);
		return platformInfo;
	}

}
