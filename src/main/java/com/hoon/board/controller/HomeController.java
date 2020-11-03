package com.hoon.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoon.board.domain.Question;
import com.hoon.board.domain.QuestionRepository;
import com.hoon.board.domain.Result;
import com.hoon.board.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Controller
public class HomeController {

    @Autowired
    private QuestionRepository questionRepository;
//루트 요청시 홈 메서드를 들어온다
  //  @GetMapping("/")
    @GetMapping(path = {"/"})
    public String home(@RequestParam(value = "page",defaultValue = "0") int page,
    				   @RequestParam(value = "size",defaultValue = "10") int size,
    				   @RequestParam(value = "sort",defaultValue = "DESC") String sort,
    				   @RequestParam(value = "col",defaultValue = "id") String col,
    				   @RequestParam(value = "title",defaultValue = "") String title,
    				   @RequestParam(value = "name",defaultValue = "") String name,
    				   @RequestParam(value = "contents",defaultValue = "") String contents,
    				   Model model, HttpSession httpSession, HttpServletRequest request) {
    	//이름 으로 정보 가져오기
    	User u = questionRepository.findByUserName("상훈1");
    	//소트해서 리스트 가져오기
    	List<User> uL = questionRepository.findByUserList(Sort.by("id").descending());
    	Page<Question> q = null;
    	//카운트 가져오기
    	long count = questionRepository.countByUserName("상훈1");
    	
    	//블리언값가져오기
    	String b = questionRepository.booleanUserName("상훈1");
    	
    	
    	
    	
    	
    	
    	//검색 타이틀 내용 이름으로 검색가능하고 페이징처리 소트 가능
    	if("ASC".equals(sort)) {
    		q = questionRepository.findBySearchPage(title,contents,name,PageRequest.of(page, size, Sort.by(col).ascending()));   		  
    	}else {
    		q = questionRepository.findBySearchPage(title,contents,name,PageRequest.of(page, size, Sort.by(col).descending()));  
    	}
    	
    	String userName = "Guest";
    	String islogin = "FALSE";
    	//파인드올로  페이징처리 소트 가능
    	if("ASC".equals(sort)) {
    		q = questionRepository.findAll(PageRequest.of(page, size, Sort.by(col).ascending()));    		  
    	}else {
    		q = questionRepository.findAll(PageRequest.of(page, size, Sort.by(col).descending()));   
    	}
    	

    	//로그인시 세션에서 이름가져오기
    	if (HttpSessionUtils.isLoginUser(httpSession)) {
    		 User loginUser = HttpSessionUtils.getUserFromSession(httpSession);
    		 userName = loginUser.getName();
    		 islogin = "TRUE";
        }
    	
    //	 Question Q = q.getContent().get(0);
    //	 Q.getCreateDate();
    //	 System.out.println(">>>>>> createDate=" + Q.getCreateDate() + ", modifiedDate=" + Q.getModifiedDate());
    //	List<Question> q = questionRepository.findAll();
        model.addAttribute("questions", q.getContent());
    	model.addAttribute("pageable",  q.getPageable());
    	model.addAttribute("totalPages",  q.getTotalPages()); 
    	model.addAttribute("totalElements",  q.getTotalElements()); 
    	model.addAttribute("size",  q.getSize()); 
    	model.addAttribute("number",  q.getNumber()); 
    	model.addAttribute("sort",  sort); 
    	model.addAttribute("col",  col);     	
        model.addAttribute("name", userName);
        model.addAttribute("islogin", islogin);
      //  Paging paging = new Paging(no);
     //   model.addAttribute("questions", questionRepository.findByPaging(paging));
      //  model.addAttribute("paging", questionRepository.obtainPaging(paging));
       
      //  return customers;

        return "index";
    }
    
    
   
    
}
