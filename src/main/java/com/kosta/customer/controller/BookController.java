package com.kosta.customer.controller;


import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.cart.model.CartVO;
import com.kosta.cart.service.CartServiceImpl;
import com.kosta.customer.model.BookDAO;
import com.kosta.customer.model.BookVO;
import com.kosta.customer.model.UsedBookDAO;

@Controller
public class BookController {

	@Autowired
	private SqlSession sqlSession;
	@Inject
	CartServiceImpl cartServiceImpl;
	
	@RequestMapping("/bookSearch.do")
	public String list(Model model, BookVO vo) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		model.addAttribute("list", bookDAO.bookSearchDao(vo));
		return "bookList/bookList";
	}	
	
	@RequestMapping("/bookWrite.do")
	public String write(BookVO vo) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		bookDAO.bookWrite(vo);
		return "redirect:../book/";
	}
	
	@RequestMapping("/bookTypeSearch.do")
	public String typeList(Model model, BookVO vo) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		String link = "bookList/bookList";
		if (vo.getType().equals("all")) {
			model.addAttribute("list", bookDAO.bookNoTypeSearchDao(vo));
		}else if(vo.getType().equals("usedSelect")){
			link = "registerForUsedBooks/usedSelect";
			model.addAttribute("list", bookDAO.usedSelectDao(vo));
		}else{
			model.addAttribute("list", bookDAO.bookTypeSearchDao(vo));
		}
		return link;
	}
	
	@RequestMapping("/bookDetail.do")
	public String bookDetail(Model model, BookVO vo) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		model.addAttribute("list", bookDAO.bookOneSearchDao(vo));
		return "bookList/bookDetail";
	}
	@RequestMapping("/usedSelect.do")
	public String usedSelect(Model model, BookVO vo) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		model.addAttribute("list", bookDAO.bookNoTypeSearchDao(vo));
		return "registerForUsedBooks/usedSelect";
	}
	
	@RequestMapping("/usedBookWrite.do")
	public String usedBookWrite(Model model, BookVO vo, HttpSession session) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		model.addAttribute("book", bookDAO.bookOneSearchDao(vo));
		model.addAttribute("id", session.getAttribute("id"));
		return "registerForUsedBooks/usedWrite";
	}
	@RequestMapping("/usedWrite.do")
	public String usedWrite(BookVO vo, HttpSession session) {
		UsedBookDAO usedBookDAO = sqlSession.getMapper(UsedBookDAO.class);
		vo.setId((String)session.getAttribute("id"));
		usedBookDAO.writeDao(vo);
		return "redirect:../book/";
	}
	// 구매랑 장바구니 할꺼임
	
	@RequestMapping("/bookCart.do")
	public String bookCart(@ModelAttribute CartVO vo, HttpSession session) throws Exception{
		if ((String)session.getAttribute("id")==null) {
			System.out.println("아이디 입력 안함");
			return "redirect:../book/";
		}
		vo.setId((String)session.getAttribute("id"));
		cartServiceImpl.insert(vo);		
		
		
		return "redirect:../book/";
	}
	
	/*@RequestMapping("/buyABook")
	public String buyABook(@ModelAttribute CartVO vo, HttpSession session) throws Exception{
		if ((String)session.getAttribute("id")==null) {
			System.out.println("아이디 입력 안함");
			return "redirect:../book/";
		}	
		cartServiceImpl.buyABook(vo);
		return "redirect:../book/";
	}*/
}
