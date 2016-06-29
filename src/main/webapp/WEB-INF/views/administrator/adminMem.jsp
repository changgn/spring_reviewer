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
				text-align: center;
				font-size: 20px;
				border: 1px solid;
				border-color: #b9b9b9;
				padding: 20px;
				color: #4c4c4c;
				width: 40%;	
				height: auto;
				margin: 40 auto;
			}
			.list{
				margin-top: 20px;
				margin: auto;
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 25px '나눔고딕', 'Nanum Gothic', sans-serif;
			}
			.content{
				border: 1px solid;
				border-color: #f6f6f6;
				width: 70%;	
				height: auto;
				margin: 40 auto;
			}
		</style>
	</head>
	<body>
		<div class="title">
			전체 회원 관리 (${count})
		</div>
		<div class="content">
			<c:forEach var="memberList" items="${memberList}">
				<table class="list">
					<tr>
						<td width="150">
							아이디 : <a id="name" href="/profile/myProfile.do?id=${memberList.id}">${memberList.id}</a>
						</td>
						<td  width="100">
							추천 : ${memberList.recommend_num}
						</td>
						<td width="200">
							가입일자 : <fmt:formatDate value="${memberList.reg_date}" pattern="yyyy-MM-dd HH:mm"/>
						</td>
						<td  width="50">
							<a id="name" href="/administrator/adminOutput.do?outId=${memberList.id}">탈퇴</a>
						</td>
					</tr>
				</table>
			</c:forEach>
		</div>
	</body>
</html>