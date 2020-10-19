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
  <body> 
  <h2>Hello! ${name}</h2> 
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
                                <span class="time">작성시간 : ${item.formattedCreateDate}</span>
                                <span class="time">수정시간 : ${item.formattedModifiedDate}</span>

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
                    <ul class="pagination center-block" style="display:inline-block;">
                        <li><a href="#">«</a></li>
                        <li><a href="/?number=1">1</a></li>
                        <li><a href="/?number=2">2</a></li>
                        <li><a href="/?number=3">3</a></li>
                        <li><a href="/?number=4">4</a></li>
                        <li><a href="/?number=5">5</a></li>
                        <li><a href="#">»</a></li>
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
