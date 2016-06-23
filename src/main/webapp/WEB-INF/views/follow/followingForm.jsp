<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

<html>
	<head>
		<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
		<script type="text/javascript">
/* 		$(document).ready(function() {
			if("${followCheck[toId]}"=="true") {
				var tag = "<a href='ingFollow.do?follow=unfollow&id=" + "${id}'" + "><img src='image/icon_36.png'></a>";
				$("#follow_button").append(tag);
			} else {
				var tag = "<a href='ingFollow.do?follow=follow&id=" + "${id}'" + "><img src='image/icon_35.png'></a>";
				$("#follow_button").append(tag);
			}
			
		}); */
/* 		$(function(){
			$('#follow_button').on({
				'click' : function(){
					if("${followCheck[toList]}"=="true") {
						var tag = "<a href='ingFollow.do?follow=unfollow&profileId=" + "${profileId}&add_id=" + "${toId}'" + "><img src='../image/icon_36.png'></a>";
						$("#follow_button").append(tag);
					} else {
						var tag = "<a href='ingFollow.do?follow=follow&profileId=" + "${profileId}&add_id=" + "${toId}'" + "><img src='../image/icon_35.png'></a>";
						$("#follow_button").append(tag);
					}
					var src = ($(this).attr('src')==='../image/icon_35.png')
					? '../image/icon_36.png'
							: '../image/icon_35.png';
					$(this).attr('src', src);
				}
			})
		}); */
		</script>
		<style>
			#followingTitle{
				min-height: 200px; 
				padding: 20px; 
				font-size: 20px; 
				margin: 0 auto; 
				background-color: #F6F6F6;
				font-size: 40px; 
				color: #4C4C4C;
			}
			#followingList{
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
		<div id="followingTitle" align="center">
			 ${profileId}님의 팔로잉
		</div>
		<c:forEach var="toId" items="${toIdList}"> 
			<div id="followingList" align="center">
				<a id="name" href="/profile/myProfile.do?id=${toId}">${toId}</a>
 				<c:choose>
					<c:when test="${followCheck[toId] eq true}">
						<a href="/follow/followingAdd.do?follow=unfollow&profileId=${profileId}&add_id=${toId}"><img src="../image/icon_36.png" align="top"></a>
					</c:when>
					<c:otherwise>	<!-- test="${followCheck eq 'false'}" -->
						<a href="/follow/followingAdd.do?follow=follow&profileId=${profileId}&add_id=${toId}"><img src="../image/icon_35.png" align="top"></a>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach> 
	</body>
</html>