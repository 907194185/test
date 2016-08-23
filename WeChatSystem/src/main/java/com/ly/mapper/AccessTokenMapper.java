package com.ly.mapper;

import org.springframework.stereotype.Repository;

import com.ly.pojo.AccessToken;

@Repository("accessTokenMapper")
public interface AccessTokenMapper {
	
	public AccessToken findAccessTokenByOne(String p_id);
	
	public void insertAccessToken(AccessToken accessToken);
	
	public void updateAccessToken(AccessToken accessToken);

}
