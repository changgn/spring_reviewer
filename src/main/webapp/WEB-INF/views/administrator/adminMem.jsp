<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<style>
			.MemberManageTitle{
				text-align: center;
				font-size: 20px;
				border: 1px solid;
				border-color: #b9b9b9;
				padding: 20px;
				color: #4c4c4c;
				width: 40%;	
				height: auto;
				margin: 40 auto;
			}
			.Member_formality_wrap{ width:400px; height:auto;lear: both; padding: auto; margin: 50 auto; position:relative; }
			.MemberInfo{width:400px; height:auto; lear: both; padding: auto; margin: 50 auto; position:relative; }
			.Member{padding: 9px 100px 9px 17px; position: relative; text-align: center;}
			
			.Member_Detaile_Info{display: none; position: fixed; z-index: 9999;top: 0;right: 0;bottom: 0;left: 0;line-height: 100%;text-align: center;}
			.Member_Simple_Info_Id{display: block;overflow: hidden; padding: 3px 12px;}
			.mem_d_i{display: inline-block;position: relative;z-index: 10000;width: 430px;background-color: #fff;line-height: normal;vertical-align: middle; top:300px;}
		</style>
		<link href="../css/style.css" rel="stylesheet" type="text/css">
		<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
		<script>	
			$(function(){
				$("body").on("click", ".Member_Simple_Info", function(e){
					e.preventDefault();
					var a = $("#menu_" + $(this).attr("id"));
				a.show();
				});
				$("body").on("click", ".Member_Detaile_Info", function(e){
					$(this).hide();
				});	
			});
		</script>
	</head>
	<body>
		<div class="MemberManageTitle">
			전체 회원 관리 (${count})
		</div>
			<c:forEach var="memberList" items="${memberList}">
				<c:if test="${memberList.id ne admin}">
					<div class="Member">
						<div align="center">
							<table>
								<tr id="${memberList.id}" class="Member_Simple_Info">
									<td width="200">
										<a href="#" id="${memberList.id}" class="Member_Simple_Info">
											아이디 : ${memberList.id}
										</a>
									</td>
									<td width="200">
										<a href="#" id="${memberList.id}" class="Member_Simple_Info">
											가입일시 : <fmt:formatDate value="${memberList.reg_date}" pattern="yyyy-MM-dd HH:mm"/>
										</a>
									</td>
								</tr>
								<tr>
									<td width="130">
										<img src="../image/recommend_off.png" width="15" height="15"> 게시글 : ${recommendCount[memberList.id]}
									</td>
									<td>
										<img src="../image/report.png"> 게시글 : ${reportCount[memberList.id]}
									</td>
								</tr>
							</table>
						</div>
						<div id="menu_${memberList.id}" class="Member_Detaile_Info">
							<div class="ly_dimmed"></div>
							<table class="mem_d_i ul_list">
								<tr>
									<td>
									아이디 : ${memberList.id}
									</td>
									<td rowspan="6" align="right">
										<a href="/profile/myProfile.do?id=${memberList.id}">
											<img src="../image/default_profile.png">
										</a>
									</td>
									<td rowspan="6" align="right">
										<a id="name" href="/administrator/adminOutput.do?outId=${memberList.id}">
											<img src="../image/memOut_con.gif">
										</a>
									</td>
								</tr>
								<tr>
									<td>
										이름 : ${memberList.name}
									</td>
								</tr>
								<tr>
									<td>
										성별 : ${memberList.gender}	
									</td>
								</tr>
								<tr>
									<td>
										email : ${memberList.email}
									</td>
								</tr>
								<tr>
									<td>
										핸드폰 번호 : ${memberList.phone_num}
									</td>
								</tr>
								<tr>
									<td>
										가입일시 : <fmt:formatDate value="${memberList.reg_date}" pattern="yyyy-MM-dd HH:mm"/>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</c:if>
			</c:forEach>
	</body>
</html>