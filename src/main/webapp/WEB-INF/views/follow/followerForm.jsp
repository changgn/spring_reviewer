<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

<html>
	<head>

		<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
		<script type="text/javascript">

 		$(document).ready(function() {
			if("${followCheck}"=="true") {
				var tag = "<a href='followerAdd.do?follow=unfollow&id="+"${id}&paramId="+"$fromId.from_id}'"+"><img src='../image/icon_36.png' height='30px' width='30px'></a>";
				$("#followerList").append(tag);
			} else {
				var tag = "<a href='followerAdd.do?follow=follow&id="+"${id}&paramId="+"${fromId.from_id}'"+"><img src='../image/icon_35.png' height='30px' width='30px'></a>";
				$("#followerList").append(tag);
			}
		}); 
		$(function(){
			$('img').on({
				'click' : function(){
					if("${followCheck}"=="true") {
						var tag = "<a href='/follow/followerAdd.do?follow=follow&id=" + "${id}&paramId=" + "${fromId.from_id}'" + "><img src='../image/icon_36.png' height='30px' width='30px'></a>";
						$("#followerList").append(tag);
					} else {
						var tag = "<a href='/follow/followerAdd.do?follow=unfollow&id=" + "${id}&paramId=" + "${fromId.from_id}'" + "><img src='../image/icon_35.png' height='30px' width='30px'></a>";
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
			div.follower{
				width: 334px; height: 59px;
				margin: auto; 
				margin-top: 200px; 
				border: 1px solid #4C4C4C;
				background-color: #066E9F;
			}
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
		<div class="follower">
			<div id="followerTitle" align="center">
				 ${id}님의 팔로워 목록 
			</div>
			<c:forEach items="${fromList}" var="fromId"> 
				<div id="followerList" align="center">
					<a href="/profile/myProfile.do?id=${fromId.from_id}">${fromId.from_id}</a>
					<%-- <c:choose>
						<c:when test="${followCheck eq 'true'}">
							<a href="/follow/followerAdd.do?follow=unfollow&id=${id}&paramId=${fromId.from_id}"><img src="../image/icon_36.png" height="30px" width="30px"></a>
						</c:when>
						<c:otherwise>	<!-- test="${followCheck eq 'false'}" -->
							<a href="/follow/followerAdd.do?follow=follow&id=${id}&paramId=${fromId.from_id}"><img src="../image/icon_35.png" height="30px" width="30px"></a>
						</c:otherwise>
					</c:choose> --%>
					
					<%-- <c:if test="${followCheck eq 'true'}">
						<a href="followerAdd.do?follow=unfollow&id=${id}"><img src="image/icon_36.png" height="30px" width="30px"></a>
					</c:if>
					<c:if test="${followCheck eq 'false'}">
						<a href="followerAdd.do?follow=follow&id=${id}"><img src="image/icon_35.png" height="30px" width="30px"></a>
					</c:if> --%>
				</div>
			</c:forEach>
		</div>
	</body>
</html>