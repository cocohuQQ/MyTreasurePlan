package com.coco.treasure.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
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
	
	@RequestMapping("/changeCurveForEveryOne")
	public String getAssetsAllocationIncreaseForEveryOne(){
		Map<String,Object> returnData = new HashMap<String,Object>();
		List<AssetsAllocationBean> result = assetsAllocationMapper.getAssetsAllocationIncreaseGroupForEveryOne();
		if(result == null || result.isEmpty()) {
			return JSONObject.toJSONString(returnData);
		}
		Set<String> names = new HashSet<>();
		Set<Integer> dates = new HashSet<>();
		Map<String, AssetsAllocationBean> datas = new HashMap<>();
		for(AssetsAllocationBean bean : result) {
			names.add(bean.getName());
			dates.add(bean.getDate());
			datas.put(bean.getDate()+"|"+bean.getName(), bean);
		}
		List<String> nameLst = new ArrayList<>(names);
		List<Integer> dateLst = new ArrayList<>(dates);
		Collections.sort(dateLst);
		Map<String, List<BigDecimal>> returnDatas = new HashMap<String, List<BigDecimal>>();
		for(Integer curDate : dateLst) {
			for(String curName : names) {
				if(!returnDatas.containsKey(curName)) {
					returnDatas.put(curName, new ArrayList<BigDecimal>());
				}
				if(datas.containsKey(curDate+"|"+curName)) {
					returnDatas.get(curName).add(datas.get(curDate+"|"+curName).getValue());
				}else {
					if(returnDatas.get(curName).size() == 0) {
						returnDatas.get(curName).add(BigDecimal.ZERO);
					}else {
						returnDatas.get(curName).add(returnDatas.get(curName).get(returnDatas.get(curName).size() - 1));
					}
				}
			}
		}
		returnData.put("dates", dateLst);
		returnData.put("names", nameLst);
		returnData.put("datas", returnDatas);
		return JSONObject.toJSONString(returnData);
	}
	
	@RequestMapping("/changeCurve/{peopleCode}")
	public String getAssetsAllocationIncrease(@PathVariable(name="peopleCode") String peopleCode){
		Map<String,Object> returnData = new HashMap<String,Object>();
		List<AssetsAllocationBean> result = assetsAllocationMapper.getAssetsAllocationIncreaseGroupByPeople(peopleCode);
		if(result == null || result.isEmpty()) {
			return JSONObject.toJSONString(returnData);
		}
		arrangeChangeCurveDatas(returnData, result);
		return JSONObject.toJSONString(returnData);
	}
	
	@RequestMapping("/changeCurve")
	public String getAssetsAllocationIncrease(){
		Map<String,Object> returnData = new HashMap<String,Object>();
		List<AssetsAllocationBean> result = assetsAllocationMapper.getAssetsAllocationIncreaseGroupByPeopleForOverView();
		if(result == null || result.isEmpty()) {
			return JSONObject.toJSONString(returnData);
		}
		arrangeChangeCurveDatas(returnData, result);
		return JSONObject.toJSONString(returnData);
	}

	private void arrangeChangeCurveDatas(Map<String, Object> returnData, List<AssetsAllocationBean> result) {
		Set<String> types = new HashSet<>();
		Set<Integer> dates = new HashSet<>();
		Map<String, AssetsAllocationBean> datas = new HashMap<>();
		for(AssetsAllocationBean bean : result) {
			types.add(bean.getAssetsTypeDes());
			dates.add(bean.getDate());
			datas.put(bean.getDate()+"|"+bean.getAssetsTypeDes(), bean);
		}
		List<String> typeLst = new ArrayList<>(types);
		List<Integer> dateLst = new ArrayList<>(dates);
		Collections.sort(dateLst);
		Map<String, List<BigDecimal>> returnDatas = new HashMap<String, List<BigDecimal>>();
		for(Integer curDate : dateLst) {
			for(String curType : typeLst) {
				if(!returnDatas.containsKey(curType)) {
					returnDatas.put(curType, new ArrayList<BigDecimal>());
				}
				if(datas.containsKey(curDate+"|"+curType)) {
					returnDatas.get(curType).add(datas.get(curDate+"|"+curType).getValue());
				}else {
					if(returnDatas.get(curType).size() == 0) {
						returnDatas.get(curType).add(BigDecimal.ZERO);
					}else {
						returnDatas.get(curType).add(returnDatas.get(curType).get(returnDatas.get(curType).size() - 1));
					}
				}
			}
		}
		returnData.put("dates", dateLst);
		returnData.put("types", typeLst);
		returnData.put("datas", returnDatas);
	}
	
}
