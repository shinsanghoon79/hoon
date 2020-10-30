package com.hoon.board.controller;

import com.hoon.board.domain.Question;
import com.hoon.board.domain.QuestionRepository;
import com.hoon.board.domain.Result;
import com.hoon.board.domain.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private QuestionRepository questionRepository;

  //  @GetMapping("/")
    @GetMapping(path = {"/", "/{no}"})
    public String home(@RequestParam(value = "page",defaultValue = "0") int page,
    				   @RequestParam(value = "size",defaultValue = "10") int size,
    				   @RequestParam(value = "sort",defaultValue = "DESC") String sort,
    				   @RequestParam(value = "col",defaultValue = "createDate") String col,
    				   Model model, HttpSession httpSession, HttpServletRequest request) {

    	Page<Question> q = null;
    	String userName = "Guest";
    	String islogin = "FALSE";
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
