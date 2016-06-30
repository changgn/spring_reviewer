<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
		<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
		<script>
			$(function(){
				$(".member").click(function(){
					var param = 
					$(location).attr("href", "/administrator/adminMemInfo.do?");
				});
			});
		</script>
	</head>
	<body>
		<div class="MemberManageTitle">
			전체 회원 관리 (${count})
		</div>
		<div class="content">
			<table class="list">
				<c:forEach var="memberList" items="${memberList}">
					<c:if test="${memberList.id ne admin}">
						<tr class="member">
							<td width="120">
								아이디 : <a href="/administrator/adminMemInfo.do?id=${memberList.id}">${memberList.id}</a>
							</td>
							<td width="220">
								가입일자 : <fmt:formatDate value="${memberList.reg_date}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</body>
</html>