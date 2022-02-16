<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/styles.css" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
 <!DOCTYPE html> 
 <html lang="ko"> 
<head>
    <title>hoon QnA | Register Form</title>
</head>
<body>
<div class="container" id="main">
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-default content-main">
            <form name="question" method="post" action="/users/create" onsubmit="return check();" enctype="multipart/form-data">

	        	<div class="form-group" onclick="document.all.file.click();">
	        			<input type="file" id="file" name="file" accept="image/*;" style="display:none;"/>
	        			<input type="hidden" id="fileName" name="fileName"/>
	        			
	        			<div class="photo"><img id="url_img1" style="height: 60px;" src="" alt="" style="display:none;"/></div>	        		
	        	</div>
                <div class="form-group">
                    <label for="userId">사용자 아이디</label>
                    <input class="form-control" id="userId" name="userId" placeholder="User ID">
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                </div>
                <div class="form-group">
                    <label for="password">비밀번호 확인</label>
                    <input type="password" class="form-control" id="password2" name="password2" placeholder="Password">
                </div>
                <div class="form-group">
                    <label for="name">이름</label>
                    <input class="form-control" id="name" name="name" placeholder="Name">
                </div>
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Email">
                </div>
                <button type="submit" class="btn btn-success clearfix pull-right">회원가입</button>
                <div class="clearfix"/>
            </form>
        </div>
    </div>
</div>

</body>
 <script type="text/javascript">

  
function readImage(input) {
    // 인풋 태그에 파일이 있는 경우
    if(input.files && input.files[0]) {
        // 이미지 파일인지 검사 (생략)
        // FileReader 인스턴스 생성
        const reader = new FileReader()
        // 이미지가 로드가 된 경우
        reader.onload = e => {
            const previewImage = document.getElementById("url_img1")
            previewImage.src = e.target.result
        }
        // reader가 이미지 읽도록 하기
        reader.readAsDataURL(input.files[0])
    }
}
// input file에 change 이벤트 부여
const inputImage = document.getElementById("file")
inputImage.addEventListener("change", e => {
    readImage(e.target)
})


  function check(){
  	  if($("#file").attr('src') == ""){
  	  	alert("사진을 업로드해 주세요");
		return false;
  	  }
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
		  
	  chkPW();
	  
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
  
 function chkPW(){
 var pw = question.password.value;
 var repw = question.password2.value;
 var num = pw.search(/[0-9]/g);
 var eng = pw.search(/[a-z]/ig);
 var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

 if(pw.length < 8 || pw.length > 20){
	//$("#pass1").attr("class","error");
  	alert("8자리 ~ 20자리 이내로 입력해주세요.");
  	return false;
 }else if(pw.search(/\s/) != -1){
	//  $("#pass1").attr("class","error");
	  alert("비밀번호는 공백 없이 입력해주세요.");
	  return false;
 }else if(num < 0 || eng < 0){
 //	$("#pass1").attr("class","error");
	  alert("영문,숫자를 혼합하여 입력해주세요.");
	  return false;
 }else {
 //	 $("#pass1").attr("class","on");
	console.log("통과"); 
    return true;  
 }

}
  </script>
</html>