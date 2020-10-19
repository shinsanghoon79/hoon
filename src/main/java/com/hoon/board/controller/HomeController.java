package com.hoon.board.controller;

import com.hoon.board.domain.Question;
import com.hoon.board.domain.QuestionRepository;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public String home(Model model, HttpSession httpSession) {
    	int page = 0;
    	int size = 20;
    	String sort = "DESC";
    	if(sort.equals("DESC")) {
    		//Direction.DESC;
    	}else {
    		
    	}
    	
    	 Page<Question> q = questionRepository.findAll(PageRequest.of(0, 20)); 
    //	List<Question> q = questionRepository.findAll();
        model.addAttribute("questions", q.getContent());
    	model.addAttribute("pageable",  q.getPageable()); 
        model.addAttribute("name", "상훈");
      //  Paging paging = new Paging(no);
     //   model.addAttribute("questions", questionRepository.findByPaging(paging));
      //  model.addAttribute("paging", questionRepository.obtainPaging(paging));
       
      //  return customers;

        return "index";
    }

}
