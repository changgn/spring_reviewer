<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

<html>
	<head>
		<meta charset="UTF-8">
		<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
		<script type="text/javascript">
/*   		$(document).ready(function() {
			if("${followCheck}"=="true") {
				var tag = "<a href='followerAdd.do?follow=unfollow&id="+"${profileId}&paramId="+"${fromList}'"+"><img src='../image/icon_36.png' height='30px' width='30px'></a>";
				$("#followerList").append(tag);
			} else {
				var tag = "<a href='followerAdd.do?follow=follow&id="+"${profileId}&paramId="+"${fromList}'"+"><img src='../image/icon_35.png' height='30px' width='30px'></a>";
				$("#followerList").append(tag);
			}
		}); */
		$(function(){
			$('img').on({
				'click' : function(){
					if("${}"=="true") {
						var tag = "<a href='/screp/ScrepList.do?&profileId=" + "${profileId}&board_num=" + "${board.board.board_num}'" + "><img src='../image/icon_36.png' height='30px' width='30px'></a>";
						$("#followerList").append(tag);
					} else {
						var tag = "<a href='/screp/ScrepList.do?&profileId=" + "${profileId}&board_num=" + "${board.board.board_num}'" + "><img src='../image/icon_35.png' height='30px' width='30px'></a>";
						$("#followerList").append(tag);
					}
					var src = ($(this).attr('src')==='../image/icon_36.png')
					? '../image/icon_35.png'
							: '../image/icon_36.png';
					$(this).attr('src', src);
				}
			})
		});
		</script>
		<style>
			div#followerTitle{
				width: 300px; height: 35px; 
				margin: 12px auto; 
				border: 1px solid #4C4C4C;
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif ; 
				font: 23px '나눔고딕', 'Nanum Gothic', sans-serif ;
				background: white;
			}
			div#followerList{
				width: 200px; height: 35px; 
				margin: 6px auto; 
				border: 1px solid #4C4C4C;
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 21px '나눔고딕', 'Nanum Gothic', sans-serif; 
				background: white;
			}
		</style>
	</head>
	
	<body>
			<div id="screpTitle" align="center">
				 ${profileId}님의 스크랩 목록 
			</div>
			
			<c:forEach items="${screpList}" var="screpId">
				<div id="screpList" align="center">
					<a href="/profile/myProfile.do?id=${screpId}">${screpId}</a>
 					<c:choose>
						<c:when test="${screpCheck.screpId eq 'true'}">
							<a href="/screp/screp.do?&profileId=${profileId}&board_num=${board.board.board_num}"><img src="../image/icon_36.png" height="30px" width="30px"></a>
						</c:when>
						<c:otherwise>	<!-- test="${followCheck eq 'false'}" -->
							<a href="/screp/screp.do?&profileId=${profileId}&board_num=${board.board.board_num}"><img src="../image/icon_35.png" height="30px" width="30px"></a>
						</c:otherwise>
					</c:choose>
				</div>
			</c:forEach>
	</body>
</html>