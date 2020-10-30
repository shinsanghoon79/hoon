<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/styles.css" rel="stylesheet">
 <!DOCTYPE html> 
 <html lang="ko"> 
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>hoon QnA | Update Form</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/styles.css" rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script type="text/javascript">
  function check(){
	  if(question.userId.value == "") {
		    alert("값을 입력해 주세요.");
		    question.id.focus();
		    return false;
		  }
	  
	  if(question.password.value == "") {
		    alert("값을 입력해 주세요.");
		    question.password.focus();
		    return false;
		  }
	  
	  if(question.password.value != question.password2.value) {
		    alert("패스워드가 서로 다릅니다 확인해주세요");
		    question.password.focus();
		    return false;
		  }
	  
	  if(question.name.value == "") {
		    alert("값을 입력해 주세요.");
		    question.name.focus();
		    return false;
		  }
	  
	  if(question.email.value == "") {
		    alert("값을 입력해 주세요.");
		    question.email.focus();
		    return false;
		  }
  }
  </script>
</head>

<body>
<div class="container" id="main">
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-default content-main">
            <form name="question" method="post" action="/users/${user.userId}"  onsubmit="return check();">
                <input type="hidden" name="_method" value="put"> <!--@PutMapping 을 사용하기 위한 방법-->
                <div class="form-group">
                    <label for="userId">사용자 아이디</label>
                    <input class="form-control" id="userId" name="userId" value="${user.userId}" placeholder="User ID" readonly>
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" value="${user.password}" placeholder="Password">
                </div>
                <div class="form-group">
                    <label for="password2">비밀번호 확인</label>
                    <input type="password" class="form-control" id="password2" name="password2" placeholder="Password">
                </div>
                <div class="form-group">
                    <label for="name">이름</label>
                    <input class="form-control" id="name" name="name" value="${user.name}" placeholder="Name">
                </div>
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" class="form-control" id="email" name="email" value="${user.email}" placeholder="Email">
                </div>
                <button type="submit" class="btn btn-success clearfix pull-right">개인정보 수정</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>