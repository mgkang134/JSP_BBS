<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "bbs.Bbs" %>
<%@ page import = "bbs.BbsDAO" %>
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
			<form method = "post" action = "update.do?bbsID=${param.bbsID}">
				<table class="table table-striped" style = "text-align:center; border:1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="2" style = "background-color:#eeeeee; text-align:center;">게시판 글 수정 양식</th>
						
						</tr>				
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class = "form-control" placeholder="글제목" name = "bbsTitle" maxlength = "50" value = "${bbs.getBbsTitle()}"></td>					
						</tr>
						
						<tr>
							<td><textarea class = "form-control" placeholder="글내용" name = "bbsContent" maxlength = "2048" style="height:350px;">${bbs.getBbsContent()}</textarea></td>					
						</tr>
					</tbody>
				</table>
				<input type = "submit" class = "btn btn-primary pull-right" value = "글수정">
			</form>
		</div>
	</div>
	
	<script src = "https://code.jquery.com/jquery-3.1.1.min.js"> </script>
	<script src = "js/bootstrap.js"> </script>
	
</body>
</html>