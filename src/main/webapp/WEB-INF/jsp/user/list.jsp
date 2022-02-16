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
    <title>hoon QnA | Member List</title>
</head>
<body>
<div class="container" id="main">
    <div class="col-md-10 col-md-offset-1">
        <div class="panel panel-default">
        <c:if test="${errorMsg != null}">
         	<div class="alert alert-danger" role="alert">${errorMsg}</div>
         </c:if>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>번호</th>
                    <th>사진</th>
                    <th>사용자 아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                  <c:forEach var="item" items="${users}" varStatus="idx"> 
                <tr>
                    <th scope="row">${item.id}</th>
                    
                    <td>
                    <c:if test="${item.fileName != null}"> 
                    	<img src="${item.fileName}" width="100px;"/>
                    </c:if>
                    </td>
                    <td>${item.userId}</td>
                    <td>${item.name}</td>
                    <td>${item.email}</td>
                    <td><a href="/users/${item.id}/form" class="btn btn-success" role="button">수정</a></td>
                </tr>

                 </c:forEach> 
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>