<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

<html>
	<head>
		<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			if("${followCheck}"=="true") {
				var tag = "<a href='ingFollow.do?follow=unfollow&id=" + "${id}'" + "><img src='image/icon_36.png'></a>";
				$("#follow_button").append(tag);
			} else {
				var tag = "<a href='ingFollow.do?follow=follow&id=" + "${id}'" + "><img src='image/icon_35.png'></a>";
				$("#follow_button").append(tag);
			}
			
		});
		$(function(){
			$('#follow_button').on({
				'click' : function(){
					if("${followCheck}"=="true") {
						var tag = "<a href='ingFollow.do?follow=unfollow&id=" + "${id}'" + "><img src='image/icon_36.png'></a>";
						$("#follow_button").append(tag);
					} else {
						var tag = "<a href='ingFollow.do?follow=follow&id=" + "${id}'" + "><img src='image/icon_35.png'></a>";
						$("#follow_button").append(tag);
					}
					var src = ($(this).attr('src')==='image/icon_35.png')
					? 'image/icon_36.png'
							: 'image/icon_35.png';
					$(this).attr('src', src);
				}
			})
		});
		</script>
		<style>
			div.following{
				width: 334px; height: 59px;
				margin: auto; 
				margin-top: 200px; 
				border: 1px solid #4C4C4C;
				background-color: #066E9F;
			}
			div#followingTitle{
				width: 300px; height: 35px; 
				margin: 12px auto; 
				border: 1px solid #4C4C4C;
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif ; 
				font: 23px '나눔고딕', 'Nanum Gothic', sans-serif ;
				background: white;
			}
			div#followingList{
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
		<img src="../../../image/icon_35.png">
		<div class="following" >
			<div id="followingTitle" align="center">
				 ${id}님의 팔로잉 목록 
			</div>
			<c:forEach var="toId" items="${toList}"> 
				<div id="followingList" align="center">
					<a href="/reviewer/profile/myProfile.do?id=${toId.to_id}">${toId.to_id}</a>
				</div>
			</c:forEach> 
		</div> 
	</body>
</html>