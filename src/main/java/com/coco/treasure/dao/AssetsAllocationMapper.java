package com.coco.treasure.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coco.treasure.bean.AssetsAllocationBean;

@Mapper
public interface AssetsAllocationMapper {

	List<AssetsAllocationBean> getAssetsAllocationGroupByPeople();
	
	List<AssetsAllocationBean> getAssetsAllocationGroupByAssetsType(String peopleCode);
	
	List<AssetsAllocationBean> getAssetsAllocationGroupByAssetsTypeForOverview();
	
	List<AssetsAllocationBean> getAssetsAllocationIncreaseGroupByPeopleForOverView();
	
	List<AssetsAllocationBean> getAssetsAllocationIncreaseGroupByPeople(String peopleCode);
	
	List<AssetsAllocationBean> getAssetsAllocationIncreaseGroupForEveryOne();
	
}
