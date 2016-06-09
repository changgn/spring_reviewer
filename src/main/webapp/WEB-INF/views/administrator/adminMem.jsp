<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<style>
			#adminMemTitle{margin: auto;}
			#MemberList{margin-top: 25px;}
		</style>
	</head>
	<body>
		<div id="adminMemTitle">
			전체 회원 관리 (${count})
		</div>
		<c:forEach var="memberList" items="${memberList}">
			<div id="MemberList">
				아이디 : <a href="Profile.do?id=${memberList.id}">${memberList.id }</a>
				추천  ${memberList.recommend_num}
				가입일자 : <fmt:formatDate value="${memberList.reg_date}" pattern="yyyy-MM-dd HH:mm"/>
			</div>
		</c:forEach>
	</body>
</html>