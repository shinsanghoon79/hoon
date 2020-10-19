package com.hoon.board.controller;

import com.hoon.board.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // 답변 하기
    @PostMapping
    public  String create(@PathVariable Long questionId, String contents, HttpSession session) {
        // 로그인되어 있지 않으면 로그인 페이지로
        if ( !HttpSessionUtils.isLoginUser(session) ) {
        	 return "redirect:/users/loginForm";
        }
        // 로그인된 회원의 정보 가져오기
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = new Answer(loginUser, question, contents);
        // 답변의 갯수 증가
        question.addAnswer();
        answerRepository.save(answer);
        return "redirect:/questions/"+question.getId();
    }

    // 답변 삭제하기
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            return Result.fail("로그인해야 합니다.");
        }

        Answer answer = answerRepository.findById(id).get();
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if ( !answer.isSameWriter(loginUser) ) {
            return Result.fail("자신의 글만 삭제할 수 있습니다.");
        }

        answerRepository.deleteById(id);
        Question question = questionRepository.findById(questionId).get();
        // 답변의 갯수 감소
        question.deleteAnswer();
        questionRepository.save(question);
        return Result.ok();
    }
}
