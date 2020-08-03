<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name = "viewport" content="width=device-width", initial-scale = "1">
<link rel = "stylesheet" href = "css/bootstrap.css">
<link rel = "stylesheet" href = "css/custom.css">
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<title>JSP 게시판 웹사이트</title>
</head>
<body>
	<nav class = "navbar navbar-default">
		<div class = "navbar-header">
			<button type = "button" class = "navbar-toggle collapsed"
				data-toggle = "collapse" data-target = "#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class = "icon-bar"></span>
				<span class = "icon-bar"></span>
				<span class = "icon-bar"></span>
				
			</button>
			<a class = "navbar-brand" href = "main.jsp">JSP 게시판 웹사이트</a>
		</div>
		<div class = "collapse navbar-collapse" id = "bs-example-navbar-collapse-1">
			<ul class = "nav navbar-nav">
				<li class = "${param.tab_main}"><a href = "main.jsp">메인</a></li>
				<li class = "${param.tab_bbs}"><a href = "list.do">게시판</a></li>
			</ul>
			<c:choose>
				<c:when test = "${empty userID}">
					<ul class = "nav navbar-nav navbar-right">
						<li class = "dropdown">
							<a href = "#" class = "dropdown-toggle"
								data-toggle = "dropdown" role = "button" aria-haspopup="true"
								aria-expanded="false">접속하기<span class="caret"></span></a>
								<ul class = "dropdown-menu">
									<li class = "${param.tab_login}"><a href = "login_view.do">로그인</a></li>
									<li class = "${param.tab_join}"><a href = "join_view.do">회원가입</a></li>
								</ul>
						</li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul class = "nav navbar-nav navbar-right">
						<li class = "dropdown">
							<a href = "#" class = "dropdown-toggle"
								data-toggle = "dropdown" role = "button" aria-haspopup="true"
								aria-expanded="false">회원관리<span class="caret"></span></a>
								<ul class = "dropdown-menu">
									<li><a href = "logout.do">로그아웃</a></li>
								</ul>
						</li>
					</ul>
				</c:otherwise>
			</c:choose>
		</div>
	</nav>
</body>
</html>