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
				max-width: 50%;
				margin-top: 20px;
				margin: auto;
				border-bottom: solid 1px;
				text-align: center;
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 30px '나눔고딕', 'Nanum Gothic', sans-serif;
			}
			#item{
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 30px '나눔고딕', 'Nanum Gothic', sans-serif;
			}
		</style>
	</head>
	<body>
		<div class="title">
			인기 게시글 관리
		</div>
		<c:forEach var="board" items="${populList}"> 
			<div class="list">
				작성자 : <a id="item" href="/profile/myProfile.do?id=${board.id}">${board.id}</a> 
				||
				추천 : ${board.recommend_num} ||
				<a id="item" href="/content/contentForm.do?board_num=${board.board_num}">상세보기</a>
				||
				<a id="item" href="/administrator/adminDelete.do?board_num=${board.board_num}">삭제</a>
			</div>
		</c:forEach>
	</body>
</html>