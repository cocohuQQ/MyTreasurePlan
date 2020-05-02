package com.coco.treasure.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coco.treasure.bean.AssetsAllocationBean;
import com.coco.treasure.dao.AssetRegMapper;

@RestController
@RequestMapping("/assetReg")
public class AssetRegController {
	
	@Autowired
	private AssetRegMapper assetRegMapper;
	
	@RequestMapping("/items")
	public String items() {
		Map<String,Map<String,Object>> returnData = new HashMap<>();
		List<Map<String,Object>> items = assetRegMapper.getPeopleAssetItems();
		if(items == null) {
			return JSONObject.toJSONString(returnData);
		}
		for(Map<String,Object> curItem : items) {
			String peopleCode = String.valueOf(curItem.get("peopleCode"));
			String peopleName = (String)curItem.get("peopleName");
			String assetsTypeCod = String.valueOf(curItem.get("assetsTypeCod"));
			String assetsTypeDescrible = (String)curItem.get("assetsTypeDescrible");
			if(!returnData.containsKey(peopleCode)) {
				returnData.put(peopleCode, new HashMap<String,Object>());
			}
			returnData.get(peopleCode).put("peopleName", peopleName);
			if(returnData.get(peopleCode).get("assets") == null) {
				returnData.get(peopleCode).put("assets", new ArrayList<Map<String,Object>>());
			}
			((ArrayList)returnData.get(peopleCode).get("assets")).add(curItem);
		}
		return JSONObject.toJSONString(returnData);
	}
	
	@RequestMapping("/regAssetForAllPeople")
	public void regAssetForAllPeople(@RequestBody List<AssetsAllocationBean> assetsAllocationBeans) {
		if(assetsAllocationBeans != null && !assetsAllocationBeans.isEmpty()) {
			assetRegMapper.insertAssetItems(assetsAllocationBeans);
		}
	}

}
