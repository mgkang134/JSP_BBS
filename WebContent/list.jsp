<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "bbs.BbsDAO" %>
<%@ page import = "bbs.Bbs" %>
<%@ page import = "java.util.ArrayList" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name = "viewport" content="width=device-width", initial-scale = "1">
<link rel = "stylesheet" href = "css/bootstrap.css">
<link rel = "stylesheet" href = "css/custom.css">
<title>JSP 게시판 웹사이트</title>
<style type = "text/css">
	a, a:hover{
		color : #000000;
		text-decoration: none;
	}
</style>
<script>
	
</script>
</head>
<body>
	<jsp:include page="/layout/navbar.jsp"></jsp:include>
	
	<div class ="container">
		<div class = "row">
			<table class="table table-striped" style = "text-align:center; border:1px solid #dddddd">
				<thead>
					<tr>
						<th style = "background-color:#eeeeee; text-align:center;">번호</th>
						<th style = "background-color:#eeeeee; text-align:center;">제목</th>
						<th style = "background-color:#eeeeee; text-align:center;">작성자</th>
						<th style = "background-color:#eeeeee; text-align:center;">작성일</th>
					</tr>				
				</thead>
				<tbody>
					<c:forEach items = "${list}" var = "bbs">
						<tr>
						<td>${bbs.bbsID}</td>		
						<td><a href = "content_view.do?bbsID=${bbs.bbsID}">${bbs.bbsTitle}</a></td>					
						<td>${bbs.userID }</td>					
						<td>${bbs.bbsDate}</td>					
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var = "curPage" value = "${paging.getCurPage()}"/>
			<c:set var = "blockStartNum" value = "${paging.getBlockStartNum()}"/>
			<c:set var = "blockLastNum" value = "${paging.getBlockLastNum()}"/>
			<c:set var = "lastPageNum" value = "${paging.getLastPageNum()}"/>
			
			<c:if test = "${curPage > 5}">
				<a href="list.do?pageNumber=${blockStartNum - 1}" class = "btn btn-success btn-arraw-left">이전</a>
			</c:if>
		
			<c:if test = "${lastPageNum > blockLastNum}">
				<a href="list.do?pageNumber=${blockLastNum + 1}" class = "btn btn-success btn-arraw-right">다음</a>
			</c:if>
			
	
			
			
			
			<a href = "write_view.do" class = "btn btn-primary pull-right">글쓰기</a>
		</div>
	</div>
	
	<script src = "https://code.jquery.com/jquery-3.1.1.min.js"> </script>
	<script src = "js/bootstrap.js"> </script>
	
</body>
</html>
