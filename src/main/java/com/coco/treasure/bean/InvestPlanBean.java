package com.coco.treasure.bean;

import java.math.BigDecimal;

public class InvestPlanBean {

	private BigDecimal addMoney; // 每年增加金额

	private BigDecimal annualInterestRate;// 年利率，单位%

	public BigDecimal getAddMoney() {
		return addMoney;
	}

	public void setAddMoney(BigDecimal addMoney) {
		this.addMoney = addMoney;
	}

	public BigDecimal getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(BigDecimal annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

}
