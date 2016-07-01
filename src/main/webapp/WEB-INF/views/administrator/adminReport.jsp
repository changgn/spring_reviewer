<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<style>
			.title{width:80%; margin:0 auto; padding:10px;}
			.report_title{
				width: 40%;	
				height: auto;
				margin: 40;
				margin-left: 10 auto;
				text-align: center;
				font-size: 20px;
				border: 1px solid;
				border-color: #b9b9b9;
				padding: 20px;
				color: #4c4c4c;
				float: left;
			}
			.popul_title{
				width: 40%;	
				height: auto;
				margin: 40;
				margin-right: 10 auto;
				text-align: center;
				font-size: 20px;
				border: 1px solid;
				border-color: #b9b9b9;
				padding: 20px;
				color: #4c4c4c;
				float: right;
			}
			.board{
				clear:both;
			}
			.list{
				margin-top: 20px;
				margin: auto;
				border-bottom: solid 1px;
				border-bottom-color: #f6f6f6;
				text-align: center;
				padding : 7px;
				color: #4c4c4c;
				font-size: 14px;
			}
			.report_board{
				border: 1px solid;
				border-color: #f6f6f6;
				width: 50%;	
				height: auto;
				margin: 40 auto;
				float: left;
			}
			.popul_board{
				border: 1px solid;
				border-color: #f6f6f6;
				width: 50%;	
				height: auto;
				margin: 40 auto;
				float: right;
			}
		</style>
	</head>
	<body>
		<div class="titel">
			<div class="report_title">
				신고 게시글 관리
			</div>
			<div class="popul_title">
				인기 게시글 관리
			</div>
		</div>
		<div class="board">
			<div class="report_board">
				<c:forEach var="rboard" items="${reportList}" > 
					<table class="list">
						<tr>
							<td width="auto">
								작성자 : <a id="text" href="/profile/myProfile.do?id=${rboard.id}"> ${rboard.id}</a>
							</td>
							<td class="space" width="10"></td>
							<td width="auto" >
								<img src="../image/report.png"> : ${rboard.report_num}
							</td>
							<td class="space" width="10"></td>
							<td width="auto">
								작성일 : <fmt:formatDate value="${rboard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td class="space" width="10"></td>
							<td width="auto">
								<a id="text" href="/content/contentForm.do?board_num=${rboard.board_num}">상세보기</a>
							</td>
							<td class="space" width="10"></td>
							<td width="auto">
								<a id="text" href="/content/deleteContent.do?board_num=${rboard.board_num}&id=${rboard.id}"><img src="../image/icon_66.png"></a>
							</td>
						</tr>
					</table>
				</c:forEach>
			</div>
			<div class="popul_board">
				<c:forEach var="pboard" items="${populList}"> 
					<table class="list">
						<tr>
							<td width="auto">
								작성자  : <a id="item" href="/profile/myProfile.do?id=${pboard.id}">${pboard.id}</a>
							</td>
							<td class="space" width="10"></td>
							<td width="auto">
								<img src="../image/recommend_off.png" width="15" height="15"> : ${pboard.recommend_num}
							</td>
							<td class="space" width="10"></td>
							<td width="auto">
								작성일 : <fmt:formatDate value="${pboard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td class="space" width="10"></td>
							<td width="auto">
								<a id="item" href="/content/contentForm.do?board_num=${pboard.board_num}">상세보기</a> 
							</td>
							<td class="space" width="10"></td>
							<td width="auto">
								<a id="item" href="/content/deleteContent.do?board_num=${pboard.board_num}&id=${pboard.id}"><img src="../image/icon_66.png"></a>
							</td>
						</tr>
					</table>
				</c:forEach>
			</div>
		</div>
	</body>
</html>