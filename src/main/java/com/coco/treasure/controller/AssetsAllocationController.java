package com.coco.treasure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coco.treasure.bean.AssetsAllocationBean;
import com.coco.treasure.dao.AssetsAllocationMapper;

@RestController
@RequestMapping("/assetsAllocation")
public class AssetsAllocationController {

	@Autowired
	private AssetsAllocationMapper assetsAllocationMapper;
	
	@RequestMapping("/groupByPeople")
	public List<AssetsAllocationBean> getAssetsAllocationGroupByPeople(){
		return assetsAllocationMapper.getAssetsAllocationGroupByPeople();
	}
}
