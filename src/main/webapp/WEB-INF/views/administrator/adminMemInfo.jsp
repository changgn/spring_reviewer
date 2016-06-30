<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<style>
			.MemberManageTitle{
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
		<div class="MemberManageTitle">
			전체 회원 관리 (${count})
		</div>
		<div class="content">
			<table class="list">
				<c:forEach var="memberList" items="${memberList}">
					<c:if test="${memberList.id ne admin}">
						<tr>
							<td width="120">
								아이디 : <a id="name" href="/profile/myProfile.do?id=${memberList.id}">${memberList.id}</a>
							</td>
							<td>
								이름 : ${memberList.name}
							</td>
							<td>
								성별 : ${memberList.gender}
							</td>
							<td>
								email : ${memberList.email}
							</td>
							<td width="220">
								가입일자 : <fmt:formatDate value="${memberList.reg_date}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td  width="40">
								<a id="name" href="/administrator/adminOutput.do?outId=${memberList.id}"><img src="../image/icon_66.png"></a>
							</td>
						</tr>
						<tr>
							<td width="120">
								작성 게시글 : ${boardCount[memberList.id]}
							</td>
							<td width="130">
								<img src="../image/recommend_off.png" width="15" height="15"> 받은 게시글 : ${recommendCount[memberList.id]}
							</td>
							<td>
								<img alt="" src=""> 받은 게시글 : 
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</body>
</html>