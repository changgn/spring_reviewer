<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

<html>
	<head>
		<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			if("${followCheck}"=="true") {
				var tag = "<a href='erFollow.do?follow=unfollow&id=" + "${id}'" + "><img src='../image/icon_36.png'></a>";
				$("#follow_button").append(tag);
			} else {
				var tag = "<a href='erFollow.do?follow=follow&id=" + "${id}'" + "><img src='../image/icon_35.png'></a>";
				$("#follow_button").append(tag);
			}
			
		});
		$(function(){
			$('#follow_button').on({
				'click' : function(){
					if("${followCheck}"=="true") {
						var tag = "<a href='erFollow.do?follow=unfollow&id=" + "${id}'" + "><img src='../image/icon_36.png'></a>";
						$("#follow_button").append(tag);
					} else {
						var tag = "<a href='erFollow.do?follow=follow&id=" + "${id}'" + "><img src='../image/icon_35.png'></a>";
						$("#follow_button").append(tag);
					}
					var src = ($(this).attr('src')==='../image/icon_35.png')
					? '../image/icon_36.png'
							: '../image/icon_35.png';
					$(this).attr('src', src);
				}
			})
		});
		</script>
		<style>

		</style>
	</head>
	
	<body>
		<div id="followerTable" >
			<div id="nameFollower" class="size_long title_find">
				 ${id}님의 팔로워 목록 
			</div>
			<div id="followerList">
				<c:forEach var="fromId" items="${fromList}"> 
					<div id="followerIdList" class="title_find">
						<a href="profile.do?id=${fromId.from_id}">${fromId.from_id}</a>
					</div>
				 </c:forEach> 
			</div>
		</div> 
	</body>
</html>