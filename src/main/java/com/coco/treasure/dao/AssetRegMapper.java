package com.coco.treasure.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.coco.treasure.bean.AssetsAllocationBean;

@Mapper
public interface AssetRegMapper {
	
	public List<Map<String,Object>> getPeopleAssetItems();
	
	void insertAssetItems(List<AssetsAllocationBean> list);
}
