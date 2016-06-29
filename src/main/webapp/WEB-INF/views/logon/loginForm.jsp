<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>

<style>
input[type=checkbox] {  
    display: none;  
}

input[type=checkbox] + label{
    display: inline-block;  
    cursor: pointer;  
    position: relative;  
    padding-left: 25px;  
    margin-right: 15px;  
    font-size: 13px;
    color: grey;
}

input[type=checkbox]+ label:before {     
    content: "";  
    display: inline-block;  
    width: 20px;  
    height: 20px;  
    margin-right: 20px;  
    position: absolute;  
    left: 0;  
    bottom: 1px;  
    border-radius: 4px; 
    box-shadow: inset 0px 1px 1px 0px rgba(0, 0, 0, .3), 0px 1px 0px 0px rgba(255, 255, 255, .8);  
}

input[type=checkbox]:checked + label:before { 
    content: "\2713";  /* 체크모양 */
    text-shadow: 1px 1px 1px rgba(0, 0, 0, .2);  
    font-size: 18px; 
    font-weight:800; 
    color: #fff;  
    background:#2f87c1;
    text-align: center;  
    line-height: 18px;  
} 

</style>
<script>

function setCookie(cookieName, value, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}

function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

//= document.cookie -> 쿠키를 읽는다.
function getCookie(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}

$(function(){
	//로그인 버튼
	$("#btn_login_submit").click(function(){
		check();
	});
	//회원 가입버튼
	$("#btn_join").click(function(){
		$(location).attr("href","/member/join.do");
	}); //아이디 비밀번호 찾기 버튼
	$("#btn_find").click(function(){
		$(location).attr("href","/idpwSearch/idpwSearchNew.do");
	});
    $("#passwd").keyup(function(e){
        if(e.keyCode == 13){
        	check();
        }
    });
    $("#login_logo").click(function(){
    	$(location).attr("href","/main/main.do");
    });
    
});
function check() {
	if($("#id").val()=="") {
		alert("아이디를 입력해 주세요");
		$("#id").focus();
		return false;
	}
	if($("#passwd").val()=="") {
		alert("비밀번호를 입력해 주세요");
		$("#passwd").focus();
		return false;
	} 
	$("#loginForm").submit();
}

</script>
<style>
#div_idsave{width : 500px; height : 30px; text-align: left; margin :0 auto; padding-left : 20px; padding-top : 3px;}
</style>
<title>로그인</title>
</head>
<body>
<c:if test="${login_status!=2}">
	<%response.sendRedirect("/logon/login.do"); %>
</c:if>
<c:if test="${message!=null}">
	<script>alert("아이디 또는 패스워드가 일치하지 않습니다");</script>
</c:if>



<div id="logon">  
	<form method="post" action="/logon/login.do" name="loginFrom" id="loginForm">
		<div id="loginForm">
			<div id="login_logo"><a href="#"><img src="../image/reviewer_gray.png" ></a></div>
			<div id="div_id" class="size_long"><input type="text" class="text_login" id="id" name="id" placeholder="아이디" ></div>
			<div id="div_passwd" class="size_long"><input type="password" class="text_login" id="passwd" name="passwd" placeholder="비밀번호"></div>	
			<div id="div_idsave" class="checkbox-style">
			<input id="divECI_ISDVSAVE"  name="autologin" type="checkbox"><label for="divECI_ISDVSAVE"> 자동 으로 로그인 하시겠습니까? </label></div>
			<div id="div_autologin"></div>
			<div id="btn_login_submit" class="btn_long"><a href="#" >로 그 인</a></div>
		</div>
	</form>
	<div id="join_find">
		<div id="btn_join" class="btn_long"><a href="#" >회 원 가 입</a></div>
		<div id="btn_find" class="btn_long"><a href="#" >아이디/비밀번호 찾기</a></div>
	</div>
</div>
</body>
</html>