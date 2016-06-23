<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<style>
			#adminMemTitle{
				min-height: 200px; 
				padding: 20px; 
				font-size: 20px; 
				margin: 0 auto; 
				background-color: #F6F6F6;
				font-size: 40px; 
				color: #4C4C4C;
			}
			#MemberList{
				margin-top: 1%;
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 35px '나눔고딕', 'Nanum Gothic', sans-serif; 
			}
			#name{
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif ; 
				font: 35px '나눔고딕', 'Nanum Gothic', sans-serif ;  
			}
		</style>
	</head>
	<body>
		<div id="adminMemTitle">
			전체 회원 관리 (${count})
		</div>
		<c:forEach var="memberList" items="${memberList}">
			<div id="MemberList">
				아이디 : <a id="name" href="/profile/myProfile.do?id=${memberList.id}">${memberList.id }</a>
				추천  ${memberList.recommend_num}
				가입일자 : <fmt:formatDate value="${memberList.reg_date}" pattern="yyyy-MM-dd HH:mm"/>
			</div>
		</c:forEach>
	</body>
</html>