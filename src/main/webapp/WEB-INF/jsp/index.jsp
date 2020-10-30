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
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>View Test Page</title> 
  </head> 
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/paginationjs/2.1.4/pagination.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/paginationjs/2.1.4/pagination.css"/>
  <script type="text/javascript">
  var totalPages = "${totalPages}";
  var totalElements = "${totalElements}";
  var size = "${size}";
  var number = "${number}";
  var pageCount = 10;
  var sort = "${sort}";
  var col = "${col}";
  $("document").ready(function(){        
      paging(totalElements, size, pageCount, number);
  });
  
  function paging(totalElements, size, pageCount, number){
	  number=eval(number)+1;
      console.log("number : " + number);
      
      var totalPage = Math.ceil(totalElements/size);    // 총 페이지 수
      var pageGroup = Math.ceil(number/pageCount);    // 페이지 그룹
      
      console.log("pageGroup : " + pageGroup);
      
      var last = pageGroup * pageCount;    // 화면에 보여질 마지막 페이지 번호
      if(last > totalPage)
          last = totalPage;
      var first = last - (pageCount-1);    // 화면에 보여질 첫번째 페이지 번호
      var next = last+1;
      var prev = first-1;
      
      if(first < 0){
    	  first = 1;
    	  prev = 1;
      }
      console.log("last : " + last);
      console.log("first : " + first);
      console.log("next : " + next);
      console.log("prev : " + prev);

      var $pingingView = $("#paging");
      
      var html = "";
      
      if(prev > 0)
          html += "<li><a href=# id='prev'><</a></li> ";
      
      for(var i=first; i <= last; i++){
          html += "<li><a href='#' id=" + i + ">" + i + "</a></li> ";
      }
      
      if(last < totalPage)
          html += "<li><a href=# id='next'>></a></li>";
      
      $("#paging").html(html);    // 페이지 목록 생성
      $("#paging a").css("color", "black");
      $("#paging a#" + number).css({"text-decoration":"none", 
                                         "color":"red", 
                                         "font-weight":"bold"});    // 현재 페이지 표시
                                         
      $("#paging a").click(function(){
    	  

          var $item = $(this);
          var $id = $item.attr("id");
          var selectedPage = $item.text();        
          if($id == "next"){selectedPage = next;}
          if($id == "prev"){selectedPage = prev;}
          
          selectedPage = selectedPage-1;
          location.href="/?page="+selectedPage+"&size=10&sort=DESC&col=id";
        //  paging(totalElements, size, pageCount, selectedPage);
      });
                                         
  }
  </script>
  <body> 
  <h2>Hello! ${name}</h2> 
  <c:if test="${islogin == 'TRUE'}">
  <button  onclick="javascript:location.href='/users/logout';">로그아웃</button>
  </c:if>
  <c:if test="${islogin != 'TRUE'}">
  <button onclick="javascript:location.href='/users/loginForm';">로그인</button>
  <button onclick="javascript:location.href='/users/form';">회원가입</button>
  </c:if>
  <div>JSP List Test</div> 
  <div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
        <div class="panel panel-default qna-list">
            <ul class="list">
  <c:forEach var="item" items="${questions}" varStatus="idx"> 
   <li>
                    <div class="wrap">
                        <div class="main">
                            <strong class="subject">
                                <a href="/questions/${item.id}">${item.title}</a>
                            </strong>
                            <div class="auth-info">
                                <i class="icon-add-comment"></i>
                                <span class="time">작성시간 : ${item.createDate}</span>
                                <span class="time">수정시간 : ${item.modifiedDate}</span>

                            </div>
                            <div class="reply" title="댓글">
                                <i class="icon-reply"></i>
                                <span class="point">${item.countOfAnswer}</span>

                            </div>
                        </div>
                    </div>
                </li>
  
  </c:forEach> 
   </ul>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6 text-center">
                	 <ul  id="paging" class="pagination center-block" style="display:inline-block;">
                	 </ul>
                </div>
                <div class="col-md-3 qna-write">
                    <a href="/questions/form" class="btn btn-primary pull-right" role="button">질문하기</a>
                </div>
            </div>
        </div>
    </div>
</div>
  </body> 
  </html>
