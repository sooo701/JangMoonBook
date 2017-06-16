package com.kosta.customer.controller;


import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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

@Controller
public class BookController {

	@Autowired
	private SqlSession sqlSession;
	@Inject
	CartServiceImpl cartServiceImpl;
	
	// 이문열이 건드린것
	@RequestMapping("/searchBook.do")
	public String searchBook(Model model, BookVO vo) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		model.addAttribute("booktype", vo.getType());
		model.addAttribute("list", bookDAO.searchBook(vo));
		return "customer/orderBook";
	}	
	
	@RequestMapping("/searchBookCategory.do")
	public String searchBookCategory(Model model, BookVO vo) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		model.addAttribute("bookCategory", vo.getCategory());
		model.addAttribute("list", bookDAO.searchBookCategory(vo));
		return "customer/orderBookCategory";
	}	
	
	@RequestMapping("/orderBook.do")
	public String bookSearchByType(Model model, BookVO vo, HttpServletRequest request) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		vo.setType(request.getParameter("type"));
		vo.setCategory(request.getParameter("category"));
		System.out.println("type : " + vo.getType());
		System.out.println("category : " + vo.getType());
		
		model.addAttribute("list", bookDAO.orderBook(vo));
		model.addAttribute("booktype", vo.getType());
		
		List list = bookDAO.bookSearchDao(vo);
		
		int contents = list.size();	// 검색된 책 갯수
		int contentsPerPage = 18;
		int currentBlock = 0;
		int currentPage = 0;
		int PagePerBlock = 5;
		int allPage = contents / contentsPerPage;	// 전체 페이지 갯수
		int allBlock = allPage / PagePerBlock;		// 전체 블록 갯수	
		
		String rCurrentPage = request.getParameter("currentPage");	
		String rCurrentBlock = request.getParameter("currentBlock");
		
		System.out.println("현제 블럭 : " + rCurrentBlock);

		if (rCurrentBlock == null) currentBlock = 1;
		else currentBlock = Integer.parseInt(rCurrentBlock);	
		if (rCurrentPage == null) currentPage = 1;
		else currentPage = Integer.parseInt(rCurrentPage);		
			
		if (currentBlock>allBlock) currentBlock=allBlock+1;
		System.out.println(currentBlock);
		if (0>=currentBlock) currentBlock=1;

		int begin = (currentBlock-1)*PagePerBlock+1;
		int suend = begin+4;
		System.out.println("수엔드 : " + suend);
		System.out.println("올 페이지 : " + allPage);
		if (suend>=allPage) suend=allPage+1;
		
		int start = (currentPage - 1)* contentsPerPage + 1;
		int end = start + contentsPerPage - 1;
		
		System.out.println("스타트 : " + start);

		String beginB = "no";
		String suendB = "no";
		
		if (begin==1) beginB = "ok";
		if (suend==allPage+1)suendB = "ok";
		
		String fullUri = request.getRequestURI();
		String uri = fullUri.substring(fullUri.lastIndexOf("/"));
		
		System.out.println("비긴 엔드 위  : "+begin+"/"+suend);

		model.addAttribute("uri", uri);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("category", vo.getCategory());
		model.addAttribute("type", vo.getType());
		model.addAttribute("contents", contents);
		model.addAttribute("currentBlock", currentBlock);
		model.addAttribute("begin", begin);
		model.addAttribute("suend", suend);
		model.addAttribute("beginB", beginB);
		model.addAttribute("suendB", suendB);

		
		
		return "customer/orderBook";
	}	
	
	@RequestMapping("/orderBookCategory.do")
	public String bookSearchByCategory(Model model, BookVO vo, HttpServletRequest request) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		vo.setType(request.getParameter("category"));
		System.out.println("카테고리 : "+vo.getCategory());
		
		List list = bookDAO.orderBookCategory(vo);
		int contents = list.size();	// 검색된 책 갯수
		int contentsPerPage = 18;
		int currentBlock = 0;
		int currentPage = 0;
		int PagePerBlock = 5;
		int allPage = contents / contentsPerPage;	// 전체 페이지 갯수
		int allBlock = allPage / PagePerBlock;		// 전체 블록 갯수	
		
		String rCurrentPage = request.getParameter("currentPage");	
		String rCurrentBlock = request.getParameter("currentBlock");
		
		System.out.println("현제 블럭 : " + rCurrentBlock);

		if (rCurrentBlock == null) currentBlock = 1;
		else currentBlock = Integer.parseInt(rCurrentBlock);	
		if (rCurrentPage == null) currentPage = 1;
		else currentPage = Integer.parseInt(rCurrentPage);		
			
		if (currentBlock>allBlock) currentBlock=allBlock+1;
		if (0>=currentBlock) currentBlock=1;

		int begin = (currentBlock-1)*PagePerBlock+1;
		int suend = begin+4;
		if (suend>=allPage) suend=allPage+1;
		
		int start = (currentPage - 1)* contentsPerPage + 1;
		int end = start + contentsPerPage - 1;

		String beginB = "no";
		String suendB = "no";
		
		if (begin==1) beginB = "ok";
		if (suend==allPage+1)suendB = "ok";
		
		String fullUri = request.getRequestURI();
		String uri = fullUri.substring(fullUri.lastIndexOf("/"));
		
		System.out.println("비긴 엔드 위  : "+begin+"/"+suend);

		model.addAttribute("uri", uri);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("category", vo.getCategory());
		model.addAttribute("type", vo.getType());
		model.addAttribute("contents", contents);
		model.addAttribute("currentBlock", currentBlock);
		model.addAttribute("begin", begin);
		model.addAttribute("suend", suend);
		model.addAttribute("beginB", beginB);
		model.addAttribute("suendB", suendB);
		model.addAttribute("list", list);
		model.addAttribute("bookCategory", vo.getCategory());
		return "customer/orderBookCategory";
	}	

	@RequestMapping("/orderBookDetail.do")
	public String bookDetail(Model model, BookVO vo, HttpServletRequest request) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		vo.setIsbn(request.getParameter("isbn"));
		model.addAttribute("bookDetail", bookDAO.orderBookDetail(vo));
		return "customer/orderBookDetail";
	}
	
	@RequestMapping("/bookType.do")
	public String bookType(Model model, BookVO vo) {
		BookDAO bookDAO = sqlSession.getMapper(BookDAO.class);
		model.addAttribute("list", bookDAO.bookSearchDao(vo));
		
		return "bookList/bookList";
	}
	
	// 이문열이 건드린것

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
