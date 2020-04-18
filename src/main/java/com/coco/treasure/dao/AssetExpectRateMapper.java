package com.coco.treasure.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coco.treasure.bean.AssetExpectRateBean;

@Mapper
public interface AssetExpectRateMapper {
	
	public List<AssetExpectRateBean> getAssetExpectRateLst();

}
