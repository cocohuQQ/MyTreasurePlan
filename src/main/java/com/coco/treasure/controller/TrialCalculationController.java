package com.coco.treasure.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coco.treasure.bean.AssetExpectRateBean;
import com.coco.treasure.dao.AssetExpectRateMapper;

@RestController
@RequestMapping("/trial")
public class TrialCalculationController {
	
	@Autowired
	private AssetExpectRateMapper assetExpectRateMapper;
	/**
	 * 为人获取目标收益率应该如何分配资产
	 * 算法：
	 * 收益率从低到高，每次挪用50%资金到上一级，直到最高级，若仍然无法达到，则再多拿50%，若小于1w，则拿完。
	 * 收益率误差控制在 -0.1% - 0.1%
	 * @param minTargetRate 目标收益率
	 * @param maxTargetRate 目标收益率
	 * @param money 总金额
	 * @return
	 */
	static class CalculateForTargetRateParamBean{
		BigDecimal minTargetRate;
		BigDecimal maxTargetRate;
		Long money;
		public void setMinTargetRate(BigDecimal minTargetRate) {
			this.minTargetRate = minTargetRate;
		}
		public void setMaxTargetRate(BigDecimal maxTargetRate) {
			this.maxTargetRate = maxTargetRate;
		}
		public void setMoney(Long money) {
			this.money = money;
		}
	}
	@RequestMapping("/reachTargetRate")
	public Map<BigDecimal,List<AssetExpectRateBean>> calculateForTargetRate(@RequestBody CalculateForTargetRateParamBean param){
		Map<BigDecimal,List<AssetExpectRateBean>> returnData = new HashMap<BigDecimal,List<AssetExpectRateBean>>();
		List<AssetExpectRateBean>  assetExpectRateLst = assetExpectRateMapper.getAssetExpectRateLst();
		if( assetExpectRateLst == null || assetExpectRateLst.isEmpty()) {
			return returnData;
			}
		//最小目标收益率
		BigDecimal mMinTargetRate = param.minTargetRate;
		if(assetExpectRateLst.get(0).getRate().compareTo(mMinTargetRate) > 0) {
			mMinTargetRate = assetExpectRateLst.get(0).getRate();
		}
		//最大目标收益率
		BigDecimal mMaxTargetRate = param.maxTargetRate;
		if(assetExpectRateLst.get(assetExpectRateLst.size() -1).getRate().compareTo(mMaxTargetRate) < 0) {
			mMaxTargetRate = assetExpectRateLst.get(assetExpectRateLst.size() -1).getRate();
		}
		
		
		for(BigDecimal targetRate = mMinTargetRate; targetRate.compareTo(mMaxTargetRate)<0 ;
				targetRate = (targetRate.add(new BigDecimal("1")) .compareTo(mMaxTargetRate) > 0 ? mMaxTargetRate: targetRate.add(new BigDecimal("1")))) {
			List<AssetExpectRateBean> curAssetExpectRateLst = new ArrayList<AssetExpectRateBean>();
			curAssetExpectRateLst.addAll(assetExpectRateLst);
			curAssetExpectRateLst.get(0).setPersent(new BigDecimal("1"));
			List<AssetExpectRateBean> calRateLst =  cal(targetRate, curAssetExpectRateLst);
            if(calRateLst != null && calRateLst.size() > 0) {
            	for( AssetExpectRateBean curAssetExpectRateBean : calRateLst) {
            		curAssetExpectRateBean.setMoney(new BigDecimal(param.money +"").multiply(curAssetExpectRateBean.getPersent()).setScale(2,BigDecimal.ROUND_HALF_UP));
            	    System.out.println(targetRate + "===" + curAssetExpectRateBean);
            	}
            	returnData.put(targetRate, calRateLst);
            }
		}
		return returnData;
	}
	
	private List<AssetExpectRateBean> cal(BigDecimal targetRate, List<AssetExpectRateBean> expectRateLst){
		if(expectRateLst.size() <= 0) {
			return null;
		}
		if(expectRateLst.size() == 1) {
			if(targetRate.compareTo(expectRateLst.get(0).getRate()) <= 0) {
				return expectRateLst;
			}else {
				return null;
			}
		}else {
			return this.cal(targetRate, expectRateLst, 0, 1);
		}
	}
	
   private List<AssetExpectRateBean> cal(BigDecimal targetRate, List<AssetExpectRateBean> expectRateLst, int fromPosition, int toPosition){
		int mToPostion = toPosition;
		while(expectRateLst.get(fromPosition).getPersent().compareTo(BigDecimal.ZERO) > 0) {
			AssetExpectRateBean fromBean = expectRateLst.get(fromPosition);
			fromBean.setPersent(fromBean.getPersent().subtract(new BigDecimal("0.1")));
			AssetExpectRateBean toBean = expectRateLst.get(mToPostion);
			toBean.setPersent(toBean.getPersent().add(new BigDecimal("0.1")));
			BigDecimal mTargetRate = BigDecimal.ZERO;
			for(AssetExpectRateBean curAssetExpectRateBean : expectRateLst) {
				mTargetRate = mTargetRate.add(curAssetExpectRateBean.getPersent()
												.multiply(curAssetExpectRateBean.getRate())
												.setScale(2,BigDecimal.ROUND_HALF_UP));
			}
			if(mTargetRate.compareTo(targetRate) > 0) {
				return expectRateLst;
			}
			if(expectRateLst.size() - 1 > mToPostion) {
				mToPostion ++;
			}else {
				mToPostion = toPosition;
			}
		}
		if(expectRateLst.size() >= fromPosition + 2 ) {
			return cal(targetRate,expectRateLst, fromPosition + 1,   fromPosition + 2);
		}else {
			return null;
		}

	}
   
   public static void main(String[] param) {
	   System.out.println(new BigDecimal(500000 +"").multiply(new BigDecimal("0.6")).setScale(2,BigDecimal.ROUND_HALF_UP));
   }
    
}
