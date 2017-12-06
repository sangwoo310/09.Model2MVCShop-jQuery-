package com.model2.mvc.service.purchase.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao{
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public PurchaseDaoImpl() {
		System.out.println(this.getClass());
	}
	
	public Purchase findPurchase(int tranNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.findPurchase", tranNo);
	}
	
	public Purchase findPurchase2(int prodNo) throws Exception {
		System.out.println("here is dao purchase2");
		return sqlSession.selectOne("PurchaseMapper.findPurchase2", prodNo);
	}
	
	public List<Purchase> getPurchaseList(Map map) throws Exception {
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
		//return ;
	}
	
	public void addPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
	}
	
	public void updatePurchase(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updateTranCode", purchase);
	}
	
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", search);
	}
	
}
