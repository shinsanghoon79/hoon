package com.hoon.board.controller;

import com.hoon.board.domain.User;
import com.hoon.board.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    // 회원가입 화면
    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    // 회원가입 처리
    @PostMapping("/create")
    public String create(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request,User user) throws IllegalStateException, IOException {
    	 String PATH = request.getSession().getServletContext().getRealPath("/") + "upload/";
         if (!file.getOriginalFilename().isEmpty()) {
             file.transferTo(new File(PATH + file.getOriginalFilename()));
             user.setFileName("/upload/"+file.getOriginalFilename());
         }
         
        userRepository.save(user);
        return "redirect:/users/list";
    }

    // 회원 리스트
    @GetMapping("/list")
    public String list(Model model, HttpServletRequest request) {
    	String messageCode = request.getParameter("messageCode");
    	if(messageCode != null && messageCode.equals("1")) {
    		 model.addAttribute("errorMsg", "자신이 쓴 글만 수정, 삭제가 가능합니다.");
    	}
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    // 로그인 화면
    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {

        User user = userRepository.findByUserId(userId);

        if ( user == null ) {
            System.out.println("login failure");
            return "redirect:/users/loginForm";
        }

        // 로그인 시 password 값과 조회하려는 password 값 비교
        if ( !user.matchPassword(password) ) {
            System.out.println("login failure");
            return "redirect:/users/loginForm";
        }

        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        System.out.println("login success");
        return "redirect:/";
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션에 담기 user 를 제거
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        System.out.println("logout success");
        return "redirect:/";
    }

    // 회원 정보수정 화면
    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {

        // session 값이 null 이면 로그인 페이지로 redirect
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            return "redirect:/users/loginForm";
        }

        // session 에 저장된 값을 sessionUser 에 복사
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        // session 에 저장된 id 값과 조회하려는 id값 비교
        if ( !sessionUser.matchId(id) ) {
        	 return "redirect:/users/list?messageCode=1";
        }

        // session 에 저장된 자신의 정보만 조회할 수 있도록 처리
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    // 회원 정보수정 처리
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) {

        // session 값이 null 이면 로그인 페이지로 redirect
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            return "redirect:/users/loginForm";
        }

        // session 에 저장된 값을 sessionUser 에 복사
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        // session 에 저장된 id 값과 조회하려는 id값 비교
        if ( !sessionUser.matchId(id) ) {
            throw new IllegalStateException("Can't modify other's information");
        }

        User user = userRepository.findById(id).get(); // 기존의 아이디 정보를 조회
        user.update(updatedUser);   // 아이디의 정보 변경
        userRepository.save(user);  // 변경된 정보를 저장
        return "redirect:/users/list";
    }
}
