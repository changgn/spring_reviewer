<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ko">
<head>
	<link rel="stylesheet" type="text/css" href="../css/style.css">
	<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
	<title><decorator:title /></title>
	<decorator:head />
</head>
<body>

	<div id="header">
	<!-- 헤더 -->
		<h1 id="logo">
			<a id="logo_btn" href="/main/main.do" title="Reviewer">Reviewer</a>
		</h1>
		<span id="main_btn">
			<a class="btn_gnb" href="/search/search.do">
				<span id="btn_search">검색 버튼</span>
			</a>
			<a class="btn_gnb" href="/logon/login.do">
				<span id="btn_login">로그인 버튼</span>
			</a>
		</span>
	</div>
	<div id="content">
		<decorator:body />
	</div>
	
	<div id="footer">
	<!-- 풋터 -->
		<ul id="list_policy">
			<li>
				<a href="#" class="list_link">이용약관</a>
			</li>
			<li>
				<a href="#" class="list_link">개인정보취급방침</a>
			</li>
		</ul>
	</div>
</body>
</html>