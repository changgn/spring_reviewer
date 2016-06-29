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
    padding-bottom: 1px;
    margin-right: 15px;  
    font-size: 16px;
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
$(document).ready(function(){
    // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
    var userInputId = getCookie("userInputId");
    $("input[name='id']").val(userInputId);  
    if($("input[name='id']").val() != ""){// 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
        $("#idsave").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
    }
    $("#idsave").change(function(){ // 체크박스에 변화가 있다면,
        if($("#idsave").is(":checked")){ // ID 저장하기 체크했을 때,
            var userInputId = $("input[name='id']").val();
            setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
        }else{ // ID 저장하기 체크 해제 시,
            deleteCookie("userInputId");
        }
    });
    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
    $("input[name='id']").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
        if($("#idsave").is(":checked")){ // ID 저장하기를 체크한 상태라면,
            var userInputId = $("input[name='id']").val();
            setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
        }
    });
});
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


//자동 로그인
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
#div_idsaveauto{margin : 20px; padding-right: 70;}

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
			<div id="div_idsaveauto">
				<input id="idsave" type="checkbox"><label for="idsave"> 아이디 저장 </label>
				<input id="autologin" name="autologin" type="checkbox"><label for="autologin"> 자동 으로 로그인 하시겠습니까? </label>
			</div>

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