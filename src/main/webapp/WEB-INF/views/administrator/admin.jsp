<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<style>
			#allBoardManagement{margin-top: 20px; padding: 20px;}
			#allMemberManagement{margin-top: 20px; 	padding: 20px;}
			#name{font-family: '나눔고딕', 'Nanum Gothic', sans-serif;	font: 35px '나눔고딕', 'Nanum Gothic', sans-serif;	}
		</style>
	</head>
	
	<body>
		<div id="adminMain" align="center">
			<div id="allBoardManagement" >
				<a id="name" href="/administrator/adminBoard.do">
					<img src="../image/list_icon.png" width="35" height="35">
					전체 게시글 관리
				</a>
			</div>
			<div id="allMemberManagement">
				<a id="name" href="/administrator/adminMem.do">
					<img src="../image/default_profile.png" width="35" height="35">
					전체 회원 관리
				</a>
			</div>
		</div>
	</body>
</html>