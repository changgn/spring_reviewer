<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<style>
			.title_heder{
				width: 40%;	
				height: auto;
				margin: 40 auto;
				
			}
			.title{
				text-align: center;
				font-size: 20px;
				border: 1px solid;
				border-color: #b9b9b9;
				padding: 20px;
				color: #4c4c4c;
			}
			.list{
				max-width: 70%;
				margin-top: 20px;
				margin: auto;
				border-bottom: solid 1px;
				border-bottom-color: #f6f6f6;
				text-align: center;
				padding : 7px;
				color: #4c4c4c;
				font-size: 14px;
			}
			.board_content{
				border: 1px solid;
				border-color: #f6f6f6;
				width: 70%;	
				height: auto;
				margin: 40 auto;
			}
			#item{
			color: #4c4c4c;
			}
		</style>
	</head>
	<body>
	<div class="title_heder">
		<div class="title">
			인기 게시글 관리
		</div>
	</div>	 
	<div class="board_content">
		<c:forEach var="board" items="${populList}"> 
			<table class="list">
				<tr>
					<td width="24%" align="left">
						작성자  : <a id="item" href="/profile/myProfile.do?id=${board.id}">${board.id}</a> &nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td width="24%" align="left">
						추천 : ${board.recommend_num}
					</td>
					<td width="20%">
						<a id="item" href="/content/contentForm.do?board_num=${board.board_num}">상세보기</a> 
					</td>
					<td width="10%">
						<a id="item" href="/administrator/adminDelete.do?board_num=${board.board_num}">삭제</a>
					</td>
				</tr>
			</table>
		</c:forEach>
	</div>
	</body>
</html>