<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<style>
			#repBoardManage{
				margin-top: 8%; 
				padding: 20px;
			}
			#popBoardManage{
				margin-top: 8%; 
				padding: 20px;
			}
			#allMemberManage{
				margin-top: 8%; 
				padding: 20px;
			}
			#name{
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif;
				font: 35px '나눔고딕', 'Nanum Gothic', sans-serif; 
			}
		</style>
	</head>
	
	<body>
		<div id="adminMain" align="center">
			<div id="repBoardManage" >
				<a id="name" href="/administrator/adminReport.do">신고 게시글 관리</a>
			</div>
			<div id="popBoardManage">
				<a id="name" href="/administrator/adminPopul.do">인기 게시글 관리</a>
			</div>
			<div id="allMemberManage">
				<a id="name" href="/administrator/adminMem.do">전체 회원 관리</a>
			</div>
		</div>
	</body>
</html>