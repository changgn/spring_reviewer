<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

<html>
	<head>
		<meta charset="UTF-8">
		<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
		<script type="text/javascript">
/*   	$(document).ready(function() {
			if("${followCheck}"=="true") {
				var tag = "<a href='followerAdd.do?follow=unfollow&id="+"${profileId}&paramId="+"${fromList}'"+"><img src='../image/icon_36.png' height='30px' width='30px'></a>";
				$("#followerList").append(tag);
			} else {
				var tag = "<a href='followerAdd.do?follow=follow&id="+"${profileId}&paramId="+"${fromList}'"+"><img src='../image/icon_35.png' height='30px' width='30px'></a>";
				$("#followerList").append(tag);
			}
		}); */
/* 		$(function(){
			$('followerList').on({
				'click' : function(){
					if("${followCheck[fromList]}"=="true") {
						var tag = "<a href='/follow/followerAdd.do?follow=follow&profileId=" + "${profileId}&add_id=" + "${fromList}'" + "><img src='../image/icon_36.png' height='30px' width='30px'></a>";
						$("#followerList").append(tag);
					} else {
						var tag = "<a href='/follow/followerAdd.do?follow=unfollow&profileId=" + "${profileId}&add_id=" + "${fromList}'" + "><img src='../image/icon_35.png' height='30px' width='30px'></a>";
						$("#followerList").append(tag);
					}
					var src = ($(this).attr('src')==='../image/icon_36.png')
					? '../image/icon_35.png'
							: '../image/icon_36.png';
					$(this).attr('src', src);
				}
			})
		}); */
		</script>
		<style>
			#followerTitle{
				min-height: 200px; 
				padding: 20px; 
				font-size: 20px; 
				margin: 0 auto; 
				background-color: #F6F6F6;
				font-size: 40px; 
				color: #4C4C4C;
			}
			#followerList{
				min-width: 200; 
				margin: 5px auto; 
				background: white;
			}
			#name{
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 35px '나눔고딕', 'Nanum Gothic', sans-serif; 
			}
		</style>
	</head>
	
	<body>
		<div id="followerTitle" align="center" >
			 ${profileId}님의 팔로워
		</div>
		<c:forEach items="${fromList}" var="fromId">
			<div id="followerList" align="center" >
				<a id="name" href="/profile/myProfile.do?id=${fromId}">${fromId}</a>
 				<c:choose>
					<c:when test="${followCheck[fromId] eq true}">
						<a href="/follow/followerAdd.do?follow=unfollow&profileId=${profileId}&add_id=${fromId}"><img src="../image/icon_36.png" align="top"></a>
					</c:when>
					<c:otherwise>	<!-- test="${followCheck eq 'false'}" -->
						<a href="/follow/followerAdd.do?follow=follow&profileId=${profileId}&add_id=${fromId}"><img src="../image/icon_35.png" align="top"></a>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach>
	</body>
</html>