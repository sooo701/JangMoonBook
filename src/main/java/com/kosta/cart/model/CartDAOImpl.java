package com.kosta.cart.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kosta.book.admin.mInventory.model.BookInfoVO;


@Repository
public class CartDAOImpl implements CartDAO {

	@Inject
	SqlSession sqlSession;
	
	@Override
	public int checkInsertEbook(CartVO vo) {
		return (int)sqlSession.selectOne("cart.checkInsertEbook", vo);
	}
	
	@Override
	public int checkInsertEbookSalelist(CartVO vo) {
		return (int)sqlSession.selectOne("cart.checkInsertEbookSalelist", vo);
	}

	@Override
	public int sumMoneyEbook(String id) {
		return (int)sqlSession.selectOne("cart.sumMoneyEbook", id);
	}

	@Override
	public List<CartVO> listCartEbook(String id) {
		return sqlSession.selectList("cart.listCartEbook", id);
	}

	@Override
	public List<CartVO> listCart(String id) {
	
		return sqlSession.selectList("cart.listCart", id);
	}

	@Override
	public void insert(CartVO vo) {
		sqlSession.insert("cart.insert",vo);
	}
	@Override
	public void delete(BookInfoVO vo) {
		sqlSession.delete("cart.delete", vo);
	}

	@Override
	public void update(int cartno) {
	
		//sqlSession.update("cart.update", cartno);
	}

	@Override
	public int sumMoney(String id) {
		
		return sqlSession.selectOne("cart.sumMoney", id);
	}

	@Override
	public int countCart(String id, String isbn) {

		Map<String,Object> map=
				new HashMap<String,Object>();
		map.put("id", id);
		map.put("isbn", isbn);
		return sqlSession.selectOne("cart.countCart",map);
	}

	@Override
	public void updateCart(CartVO vo) {
		sqlSession.update("cart.update", vo);
		
	}

	@Override
	public void modifyCart(CartVO vo) {
		sqlSession.update("cart.modify", vo); 
	}

	@Override
	public String getDiscountRate(String id) {
		return sqlSession.selectOne("cart.getDiscountRate", id);
	}

	

	
	
/*

	@Override
	public List<CartVO> cartMoney() {
		return sqlSession.selectList("cart.cart_money"); 
	}
*/
}
