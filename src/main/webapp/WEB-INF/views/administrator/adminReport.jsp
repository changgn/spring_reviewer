<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<style>
			.repManage{
				min-height: 200px; 
				padding: 20px; 
				font-size: 20px; 
				margin: 0 auto; 
				background-color: #F6F6F6;
				font-size: 40px; 
				color: #4C4C4C;
			}
			#repList{
				margin-top: 1%;
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 35px '나눔고딕', 'Nanum Gothic', sans-serif; 
			}
			#name{
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 35px '나눔고딕', 'Nanum Gothic', sans-serif; 
			}
		</style>
	</head>
	<body>
		<div class="repManage">
			신고 게시글 관리
		</div>
		<c:forEach var="board" items="${reportList}" > 
			<div id="repList">
					작성자 : <a id="name" href="/profile/myProfile.do?id=${board.id}"> ${board.id}</a>
					신고 : ${board.report_num}
					<a id="name" href="/content/contentForm.do?board_num=${board.board_num}">상세보기</a>
					<a id="name" href="/administrator/adminDelete.do?board_num=${board.board_num}">삭제</a>
			</div>
		</c:forEach>
	</body>
</html>