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
</head>
<body>
	<jsp:include page="/layout/navbar.jsp">
		<jsp:param name = "tab_bbs" value = "active"/>
	</jsp:include>
	
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
						<td>${bbs.userID}</td>					
						<td>${bbs.bbsDate}</td>					
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
			
		<c:set var = "curPage" value = "${paging.getCurPage()}"/>
		<c:set var = "blockStartNum" value = "${paging.getBlockStartNum()}"/>
		<c:set var = "blockLastNum" value = "${paging.getBlockLastNum()}"/>
		<c:set var = "lastPageNum" value = "${paging.getLastPageNum()}"/>
		
		<nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
		  
		  	<c:set var="isDisabled" value = ""/>
		  	<c:choose>
		  		<c:when test="${curPage <= 5}">
		  			<li class="page-item disabled" ><a class="page-link" tabindex="-1" href="#">이전</a></li>
		  		</c:when>
		  		<c:otherwise>
		  			<li class="page-item" ><a class="page-link" href="list.do?pageNumber=${blockStartNum - 1}">이전</a></li>
		  		</c:otherwise>
		  	</c:choose>
		  	
			<c:forEach var="i" begin="${ blockStartNum }" end="${ blockLastNum }">
	            <c:choose>
	                <c:when test="${ i > lastPageNum }">
	                	<li class="page-item disabled">
	                		<a class="page-link" href="#" tabindex="-1">${ i }</a>
                		</li>
	                </c:when>
	                
	                <c:when test="${ i == curPage }">
	                    <li class="page-item active">
	                    	<a class="page-link" href="list.do?pageNumber=${ i }">${ i }</a>
	                    </li>
	                </c:when>
	                
	                <c:otherwise>
	                	<li class="page-item"><a class="page-link" href="list.do?pageNumber=${ i }">${ i }</a></li>
	                </c:otherwise>
	           	</c:choose>
        	</c:forEach>
        	
        	<c:choose>
		  		<c:when test="${lastPageNum <= blockLastNum}">
		  			<li class="page-item disabled" ><a class="page-link" tabindex="-1" href="#">다음</a></li>
		  		</c:when>
		  		<c:otherwise>
		  			<li class="page-item" ><a class="page-link" href="list.do?pageNumber=${blockLastNum + 1}">다음</a></li>
		  		</c:otherwise>
		  	</c:choose>
        	
		  </ul>
		  
		  <a href = "write_view.do" class = "btn btn-primary pull-right">글쓰기</a>
		</nav>
		<nav aria-label = "search bar">
			<div class = "md-form mt-0">
				<form name = "f" action = "list.do" method = "get">
					<input class = "form-control" name = "query" id = "query" type = "text" placeholder="Search" aria-label = "Search">
					<input type = "submit" value = "검색">
				</form>
				
			</div>
		</nav>
		
		
	</div>
	
		
	
	<script src = "https://code.jquery.com/jquery-3.1.1.min.js"> </script>
	<script src = "js/bootstrap.js"> </script>
	
</body>
</html>
