package com.coco.treasure.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coco.treasure.bean.AssetsAllocationBean;

@Mapper
public interface AssetsAllocationMapper {

	List<AssetsAllocationBean> getAssetsAllocationGroupByPeople();
}
