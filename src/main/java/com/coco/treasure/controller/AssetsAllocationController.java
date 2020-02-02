package com.coco.treasure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping("/groupByType/{peopleCode}")
	public List<AssetsAllocationBean> getAssetsAllocationGroupByType(@PathVariable(name="peopleCode") String peopleCode){
		return assetsAllocationMapper.getAssetsAllocationGroupByAssetsType(peopleCode);
	};
	
	@RequestMapping("/groupByType")
	public List<AssetsAllocationBean> getAssetsAllocationGroupByType(){
		return assetsAllocationMapper.getAssetsAllocationGroupByAssetsTypeForOverview();
	};
	
}
