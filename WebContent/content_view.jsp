<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "bbs.Bbs" %>
<%@ page import = "bbs.BbsDAO" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name = "viewport" content="width=device-width", initial-scale = "1">
<link rel = "stylesheet" href = "css/bootstrap.css">
<link rel = "stylesheet" href = "css/custom.css">
<title>JSP 게시판 웹사이트</title>
</head>
<body>
	<jsp:include page="/layout/navbar.jsp"/>
	
	<div class ="container">
		<div class = "row">
			<table class="table table-striped" style = "text-align:center; border:1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3" style = "background-color:#eeeeee; text-align:center;">게시판 글보기</th>
					</tr>				
				</thead>
				<tbody>
					<tr>
						<td style = "width: 20%;">글 제목</td>					
						<td colspan = "2">${bbs.getBbsTitle()}</td>
					</tr>
					<tr>
						<td >작성자</td>					
						<td colspan = "2">${bbs.getUserID()}</td>
					</tr>
					<tr>
						<td >작성일자</td>		
						<td colspan = "2">${bbs.getBbsDate()}</td>
					</tr>
					<tr>
						<td >내용</td>					
						<td colspan = "2" style = min-height: 200px; text-align = left;>${bbs.getBbsContent()}</td>
					</tr>
				</tbody>
			</table>
			<a href = "list.do" class = "btn btn-primary">목록</a>
			
			<c:if test = "${userID!=null && userID.equals(bbs.getUserID())}">
				<a href = "update.jsp?bbsID=${bbs.getBbsID()}" class = "btn btn-primary">수정</a>
				<a onclick = "return confirm('정말로 삭제하시겠습니까?')" href = "delete.do?bbsID=${bbs.getBbsID()}" class = "btn btn-primary">삭제</a>
			</c:if>
			
		</div>
	</div>
	
	<script src = "https://code.jquery.com/jquery-3.1.1.min.js"> </script>
	<script src = "js/bootstrap.js"> </script>
	
</body>
</html>