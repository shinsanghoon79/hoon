package com.hoon.board.controller;

import com.hoon.board.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // 답변 하기
    @PostMapping
    public  RedirectView create(@PathVariable Long questionId, String contents, HttpSession session) {
        // 로그인되어 있지 않으면 로그인 페이지로
        if ( !HttpSessionUtils.isLoginUser(session) ) {
        	 return new RedirectView("/users/loginForm");
        }
        // 로그인된 회원의 정보 가져오기
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = new Answer(loginUser, question, contents);
        // 답변의 갯수 증가
        question.addAnswer();
        answerRepository.save(answer);
        return new RedirectView("/questions/"+question.getId());
      //  return "redirect:/questions/"+question.getId();
    }

    // 답변 삭제하기
    @DeleteMapping("/{id}")
    public RedirectView delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Model model) {
        Question question = questionRepository.findById(questionId).get();
        Answer answer = answerRepository.findById(id).get();
        Result result = valid(session, answer);
  	  if ( !result.isValid() ) {
  	       if("로그인이 필요합니다.".equals(result.getErrorMsg())) {
  	    	   return new RedirectView("/users/loginForm");
  	       	}else {
  	       		return new RedirectView("/questions/"+questionId+"?messageCode=1");
  	       	}
          }
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            //return Result.fail("로그인해야 합니다.");
        	// 에러 메시지 저장
            model.addAttribute("errorMsg", "로그인해야 합니다.");
            
            return new RedirectView("/users/loginForm");
        }

        answerRepository.deleteById(id);
        // 답변의 갯수 감소
        question.deleteAnswer();
        questionRepository.save(question);
        return new RedirectView("/questions/"+questionId);
    }
    
 // 질문 수정 화면
    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long questionId, @PathVariable Long id, Model model, HttpSession session) {
        // 현재 질문 조회
    	Answer answer = answerRepository.findById(id).get();
        Result result = valid(session, answer);
	  if ( !result.isValid() ) {
	       if("로그인이 필요합니다.".equals(result.getErrorMsg())) {
	       		 // 에러 메시지 저장
	               model.addAttribute("errorMsg", result.getErrorMsg());
	       		return "/user/login";
	       	}else {
	       		 // 에러 메시지 저장
	            model.addAttribute("errorMsg", result.getErrorMsg());
	            model.addAttribute("question", questionRepository.findById(questionId).get());
	            model.addAttribute("answers", answerRepository.findAllByQuestionId(questionId));
	       	    return "/qna/updateForm";
	       	}
        }
	  
        // 질문 수정화면으로 이동
	  	model.addAttribute("questionId", questionId);
        model.addAttribute("answer", answer);
        return "/qna/answerUpdateForm";
    }
    
    // 권한체크 메서드 1 : Result 클래스를 작성하여 구현
    private Result valid(HttpSession session, Answer answer) {
        // 로그인 여부 체크
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            return Result.fail("로그인이 필요합니다.");
        }
        // 본인여부 체크
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if ( !answer.isSameWriter(loginUser) ) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }
    
    // 질문 수정 처리
    @PutMapping("/{id}")
    public String update(@PathVariable Long questionId, @PathVariable Long id, String contents, Model model, HttpSession session) {
        // 현재 질문 조회
    	Answer answer = answerRepository.findById(id).get();
       
        Result result = valid(session, answer);
        if ( !result.isValid() ) {
        	if("로그인이 필요합니다.".equals(result.getErrorMsg())) {
	       		 // 에러 메시지 저장
	               model.addAttribute("errorMsg", result.getErrorMsg());
	       		return "/user/login";
	       	}else {
	       		 // 에러 메시지 저장
	            model.addAttribute("errorMsg", result.getErrorMsg());
	       		model.addAttribute("answer", answer);
	       	    return "/qna/show";
	       	}
        }
        // 업데이트 처리
        answer.update(contents);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }
}
