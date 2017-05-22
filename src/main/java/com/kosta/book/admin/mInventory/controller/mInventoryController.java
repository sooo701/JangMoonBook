package com.kosta.book.admin.mInventory.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.book.admin.login.model.EmployeeVO;
import com.kosta.book.admin.mInventory.model.BookInfoVO;
import com.kosta.book.admin.mInventory.model.InventoryDAO;
import com.kosta.book.admin.mInventory.model.OrderListVO;

@Controller
public class mInventoryController {
	
	@Autowired
	SqlSession sqlSession;
	
	@RequestMapping("inventoryMain.do")
	public ModelAndView inventoryMainForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		EmployeeVO vo = (EmployeeVO) session.getAttribute("user");
		String branch = vo.getBranch();
		
		System.out.println("branch = " + branch);
		
		ModelAndView mav = new ModelAndView();
		
		InventoryDAO dao = sqlSession.getMapper(InventoryDAO.class);
		List list = dao.selectEmergency(branch);
		List list2 = dao.selectNormal(branch);
		List list3 = dao.selectOrderList(branch);
		
		mav.setViewName("/admin/manage/inventoryMain");
		mav.addObject("list", list);
		mav.addObject("list2", list2);
		mav.addObject("list3", list3);
		return mav;
	}
	
	@RequestMapping("orderInven.do")
	public String orderInven(OrderListVO vo) {
		
		System.out.println("branch = " + vo.getBranch());
	
		InventoryDAO dao = sqlSession.getMapper(InventoryDAO.class);
		
		// cost, publisher를 얻는 쿼리문을 작성하여 set 메소드에 입력
		
		
		
		// 날짜처리 => db에서 sysdate로 처리했읍니다
		
		vo.setPublisher(dao.getPublisher(vo.getIsbn()));
		vo.setCost(dao.getCost(vo.getIsbn()));		
		
		dao.insertOrderList(vo);
		return "redirect:inventoryMain.do";
		
	}
	
	@Transactional
	@RequestMapping("orderConfirm.do")
	public String orderConfirm(OrderListVO vo) {
		
		System.out.println("orderConfirm.do");
		
		System.out.println("quantity = " + vo.getQuantity());
		System.out.println("isbn = " + vo.getIsbn());
		InventoryDAO dao = sqlSession.getMapper(InventoryDAO.class);
		dao.orderConfirm(vo);
		dao.deleteOrder(vo);
		
		
		return "redirect:inventoryMain.do";
	}
	
	@RequestMapping("insertNewBook.do")
	public String newBookInsert(BookInfoVO vo) {
		
		System.out.println("insertNewBook.do");
		
		InventoryDAO dao = sqlSession.getMapper(InventoryDAO.class);
		dao.newBookInsert(vo);
		
		
		return "redirect:inventoryMainForm.do";
	}

}
