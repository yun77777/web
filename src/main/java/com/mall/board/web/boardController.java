package com.mall.board.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.mall.common.PaginationVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mall.board.service.boardService;



@Controller
public class boardController {
	
	private static final Logger logger = LoggerFactory.getLogger(boardController.class);
	
	@Resource(name = "boardService")
	private boardService boardService;
	
	@RequestMapping(value = "/test.do")
	public String test(@RequestParam(defaultValue="1") int currentPageNo, @RequestParam(defaultValue="20") int recordCountPerPage,
			@RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request, Model model) throws Exception {
		
		return "test";
	}
	@RequestMapping(value = "/about.do")
	public String about(@RequestParam(defaultValue="1") int currentPageNo, @RequestParam(defaultValue="20") int recordCountPerPage,
			@RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request, Model model) throws Exception {
		
		return "about";
	}
	
	@RequestMapping(value = "/boardList.do")
	public String boardList(@RequestParam(defaultValue="1") int currentPageNo, @RequestParam(defaultValue="5") int recordCountPerPage,
			@RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request, Model model) throws Exception {
		System.err.println("searchList@@@");
		System.err.println(paramMap);
		
		paramMap.put("recordCountPerPage", recordCountPerPage);
		paramMap.put("currentPageNo", currentPageNo);
		
		try {
			PaginationVO pg = new PaginationVO(currentPageNo, recordCountPerPage, 3, 
					boardService.selectBoardListCnt(paramMap));
			
			paramMap.put("length",recordCountPerPage);
			paramMap.put("start",pg.getFirstRecordIndex()-1);
			
			List<Map<String,Object>> list=boardService.selectBoardList(paramMap);
			
			model.addAttribute("list",list);
			model.addAttribute("paramMap",paramMap);
			model.addAttribute("pg",pg);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	return "board/boardList";
	}
	
	@RequestMapping(value = "/boardDetail.do")
	public String boardDetail(
			@RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request, Model model) throws Exception {
		try {
			Map<String,Object> detail=boardService.selectBoardDetail(paramMap);
			System.err.println("detailP");
			System.err.println(paramMap);
//			int no=Integer.parseInt(paramMap.get("no").toString());
//			System.err.println("no:"+no);
//			
//			//rownum
//			paramMap.put("contentNo",no-1);
//			Map<String,Object> beforeContent=boardService.selectContent(paramMap);
//			paramMap.put("contentNo",no+1);
//			Map<String,Object> afterContent=boardService.selectContent(paramMap);
			
			
			List<Map<String,Object>> list=boardService.selectBoardHisList(paramMap);

//			System.err.println("before@@@");
//			System.err.println(beforeContent);
//			System.err.println("after@@@");
//			System.err.println(afterContent);
			
			model.addAttribute("detail",detail);
			model.addAttribute("list",list);
//			model.addAttribute("beforeContent",beforeContent);
//			model.addAttribute("afterContent",afterContent);
			System.err.println("detaiL@@@@@@@");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	return "board/boardDetail";
	}
	
	@RequestMapping(value = "/boardInsert.do")
	public String boardInsert(
			@RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request, Model model) throws Exception {
		try {
			paramMap.put("no",boardService.selectBoardMaxNo(paramMap)+1);
			model.addAttribute("paramMap",paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	return "board/boardInsert";
	}
	
	@ResponseBody
	@RequestMapping(value = "/insertBoard.do")
	public Map<String,Object> insertBoard(
			MultipartHttpServletRequest multi, @RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request, Model model) throws Exception {
		System.err.println("@@@@@@@@@@@@@@@@@@@");
		System.err.println(paramMap.get("content"));
		System.err.println("@@@@@@@@@@@@@@@@@@@");
		try {
			if(paramMap.get("no").toString()!=null||!paramMap.get("no").toString().trim().equals(""))
				paramMap.put("no",paramMap.get("no"));
			boardService.insertBoard(paramMap, multi, request);
			model.addAttribute("paramMap", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return paramMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteBoard.do")
	public Map<String,Object> deleteBoard(
			MultipartHttpServletRequest multi, @RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request, Model model) throws Exception {
		
		try {
			boardService.deleteBoard(paramMap, multi, request);
			model.addAttribute("paramMap", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return paramMap;
	}
	
//	@RequestMapping(value = "/signIn.do")
//	public String signIn( @RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request, Model model) throws Exception {
//		System.err.println("searchList@@@");
//		System.err.println(paramMap);
//		
//		try {
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	return "signIn";
//	}
//	
//	//memberLogin
//	
//	@ResponseBody
//	@RequestMapping(value = "/memberLogin.do")
//	public Map<String,Object> memberLogin(
//			MultipartHttpServletRequest multi, @RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request, Model model) throws Exception {
//		try {
//			System.err.println("ff:"+paramMap);
//			if(boardService.memberLogin(paramMap)==1)
//				paramMap.put("result","success");
//			else
//				paramMap.put("result","fail");
//				
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//		return paramMap;
//	}
//	
//	
//	@RequestMapping(value = "/signUp.do")
//	public String signUp( @RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request, Model model) throws Exception {
//		System.err.println("searchList@@@");
//		System.err.println(paramMap);
//		
//		try {
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	return "signUp";
//	}
//	
//	@ResponseBody
//	@RequestMapping(value = "/insertMember.do")
//	public Map<String,Object> insertMember(
//			MultipartHttpServletRequest multi, @RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request, Model model) throws Exception {
//		try {
//			boardService.insertMember(paramMap, multi, request);
//			model.addAttribute("paramMap", paramMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//		return paramMap;
//	}
}
