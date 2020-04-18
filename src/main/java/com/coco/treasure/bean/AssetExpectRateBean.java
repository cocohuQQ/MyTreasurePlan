package com.coco.treasure.bean;

import java.math.BigDecimal;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

public class AssetExpectRateBean {
	private int date;
	private int assetType;
	private String assetDescrible;
	private BigDecimal rate;
	private BigDecimal persent = BigDecimal.ZERO;
	private BigDecimal money;
	
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getPersent() {
		return persent;
	}
	public void setPersent(BigDecimal persent) {
		this.persent = persent;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int getAssetType() {
		return assetType;
	}
	public void setAssetType(int assetType) {
		this.assetType = assetType;
	}
	public String getAssetDescrible() {
		return assetDescrible;
	}
	public void setAssetDescrible(String assetDescrible) {
		this.assetDescrible = assetDescrible;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	@Override
	public String toString() {
		return this.assetDescrible + "|" + this.rate+"|"+this.persent+"|"+ this.money;
	}
}
