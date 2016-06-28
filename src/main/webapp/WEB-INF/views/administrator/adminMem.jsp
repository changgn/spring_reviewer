<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<style>
			.title{
				min-height: 100px; 
				padding: 10px; 
				margin: 0 auto; 
				background-color: #F6F6F6;
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 40px '나눔고딕', 'Nanum Gothic', sans-serif; 
				color: #4C4C4C;
			}
			.list{
				margin-top: 20px;
				margin: auto;
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 25px '나눔고딕', 'Nanum Gothic', sans-serif;
			}
			#name{
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 25px '나눔고딕', 'Nanum Gothic', sans-serif;
			}
		</style>
	</head>
	<body>
		<div class="title">
			전체 회원 관리 (${count})
		</div>
		<c:forEach var="memberList" items="${memberList}">
			<table class="list">
				<tr>
					<td class="list" width="250">
						아이디 : <a id="name" href="/profile/myProfile.do?id=${memberList.id}">${memberList.id}</a>
					</td>
					<td class="list" width="150">
						추천 : ${memberList.recommend_num}
					</td>
					<td class="list" width="400">
						가입일자 : <fmt:formatDate value="${memberList.reg_date}" pattern="yyyy-MM-dd HH:mm"/>
					</td>
					<td class="list" width="100">
						<a id="name" href="/administrator/adminOutput.do?outId=${memberList.id}">탈퇴</a>
					</td>
				</tr>
				
			</table>
		</c:forEach>
	</body>
</html>