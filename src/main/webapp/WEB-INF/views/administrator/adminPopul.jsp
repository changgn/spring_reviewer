<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<style>
			.recmanage{width : 300px; height: 50px; margin: 0 auto; margin-top: 50px; margin-bottom: 40px; font-size: 40px;}
			#rpid{padding:inherit; margin-top: 50px; font-size: 20px; border: }
			#recommendNum{font-size: 20px; border: medium;}
			#recButton{font-size: 20px;}
			#populTitle{margin: 0 auto; margin-top: 20px; padding-top: 11px;}
		</style>
	</head>
	<body>
		<div id="populTitle" class="size_long title_find">
			인기 게시글 관리
		</div>
		<c:forEach var="board" items="${populList}"> 
			<div id="popBoardList">
				<div id="rpid">작성자 : ${board.id}</div>
				<div id="recommendNum">추천 ${board.recommend_num}</div>
				<div id="recButton">
					<a href="content.do?board_num=${board.board_num}"><input type="button" value="상세보기"></a>
					<a href="adminDelete.do?board_num=${board.board_num}"><input type="button" value="삭제"></a>
				</div>
			</div>
		</c:forEach>
	</body>
</html>