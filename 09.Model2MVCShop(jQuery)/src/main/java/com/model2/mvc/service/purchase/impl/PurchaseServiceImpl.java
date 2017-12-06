package com.model2.mvc.service.purchase.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Repository("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService{
	
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}
	
	public PurchaseServiceImpl() {
		System.out.println(this.getClass());
	}
	
	public Purchase findPurchase(int tranNo) throws Exception {
		return purchaseDao.findPurchase(tranNo);
	}
	
	public Purchase findPurchase2(int prodNo) throws Exception {
		System.out.println("here is a findpurchase2");
		return purchaseDao.findPurchase2(prodNo);
	}
	
	public Map<String, Object> getPurchaseList(Search search,String buyerId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);
		List<Purchase> list = (List<Purchase>) purchaseDao.getPurchaseList(map);
		int totalCount = purchaseDao.getTotalCount(search);
		
		//Map<String, Object> map1 = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}
	
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.addPurchase(purchase);
	}
	
	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}
	
}
