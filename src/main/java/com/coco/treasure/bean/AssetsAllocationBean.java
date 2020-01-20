package com.coco.treasure.bean;

import java.math.BigDecimal;

public class AssetsAllocationBean {
	
	private String name;
	
	private String assetsTypeDes;
	
	private BigDecimal value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssetsTypeDes() {
		return assetsTypeDes;
	}

	public void setAssetsTypeDes(String assetsTypeDes) {
		this.assetsTypeDes = assetsTypeDes;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
