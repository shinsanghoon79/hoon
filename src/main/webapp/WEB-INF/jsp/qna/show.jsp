<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/styles.css" rel="stylesheet">
<!DOCTYPE html>
<html lang="kr">
<head>
    <title>hoon QnA | QnA Post</title>
</head>
<body>
<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-12">

        <div class="panel panel-default">
        <c:if test="${errorMsg != null}">
         	<div class="alert alert-danger" role="alert">${errorMsg}</div>
         </c:if>
            <header class="qna-header">
                <h2 class="qna-title">${errorMsg}${question.title}</h2>
            </header>
            <div class="content-main">
                <article class="article">
                    <div class="article-header">
                        <div class="article-header-thumb">
                            <img src="https://graph.facebook.com/v2.3/100000059371774/picture" class="article-author-thumb" alt="">
                        </div>
                        <div class="article-header-text">
                            <a href="/users/92/kimmunsu" class="article-author-name">${question.writer.userId}</a>
                            <a href="/questions/413" class="article-header-time" title="퍼머링크">
                                ${question.createDate}
                                <i class="icon-link"></i>
                            </a>
                        </div>
                    </div>
                    
                    <div class="article-doc">
                       ${question.contents}
                    </div>
                    <div class="article-util">
                        <ul class="article-util-list">
                            <li>
                                <a class="link-modify-article" href="/questions/${question.id}/form">수정</a>
                            </li>
                            <li>
                                <form class="form-delete" action="/questions/${question.id}" method="POST">
                                    <input type="hidden" name="_method" value="DELETE">
                                    <button class="link-delete-article" type="submit">삭제</button>
                                </form>
                            </li>
                            <li>
                                <a class="link-modify-article" href="/">목록</a>
                            </li>
                        </ul>
                    </div>
                </article>

                <div class="qna-comment">
                    <div class="qna-comment-slipp">
                        <p class="qna-comment-count"><strong>${question.countOfAnswer}</strong>개의 의견</p>
                        <div class="qna-comment-slipp-articles">
       
  				<c:forEach var="item" items="${answers}" varStatus="idx"> 
                            <article class="article" id="answer-1405">
                                <div class="article-header">
                                    <div class="article-header-thumb">
                                        <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                                    </div>
                                    <div class="article-header-text">
                                        <a href="/users/1/자바지기" class="article-author-name">${item.writer.userId}</a>
                                        <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                                            ${itemcreateDate}
                                        </a>
                                    </div>
                                </div>
                                <div class="article-doc comment-doc">
                                   ${item.contents}
                                </div>
                                <div class="article-util">
                                    <ul class="article-util-list">
                                        <li>
                                            <a class="link-modify-article" href="">수정</a>
                                        </li>
                                        <li>
                                            <a class="link-delete-article" href="/api/questions/${question.id}/answers/${item.id}">삭제</a>
                                        </li>
                                    </ul>
                                </div>
                            </article>
 					</c:forEach> 
                            <form class="answer-write" method="post" action="/api/questions/${question.id}/answers">
                                <div class="form-group" style="padding:14px;">
                                    <textarea class="form-control" placeholder="Update your status" name="contents"></textarea>
                                </div>
                                <input type="submit" class="btn btn-success pull-right" value="답변하기">
                                <div class="clearfix"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script type="text/template" id="answerTemplate">
    <article class="article">
        <div class="article-header">
            <div class="article-header-thumb">
                <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
            </div>
            <div class="article-header-text">
                <a href="#" class="article-author-name">{0}</a>
                <div class="article-header-time">{1}</div>
            </div>
        </div>
        <div class="article-doc comment-doc">
            {2}
        </div>
        <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="">수정</a>
                </li>
                <li>
                    <a class="link-delete-article" href="/api/questions/{3}/answers/{4}">삭제</a>
                </li>
            </ul>
        </div>
    </article>
</script>

</body>
</html>