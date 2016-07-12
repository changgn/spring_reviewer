<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>


<!DOCTYPE html>
<html>
<head>
<title>회원탈퇴</title>
<style>
#remove {
	margin: 0 auto;
	margin-top: 200px;
}
</style>
<script>
$(function(){
	$("#remove_btn_remove").click(function(){
		if($("#passwd").val()=="") {
			alert("비밀번호를 입력하세요");
			return false;
		} else {
			$("#deleteForm").submit();
		}
	});
});
</script>
</head>
<body>
<div id="remove">
	<form action="/member/delete.do" method="post" name="deleteForm" id="deleteForm"> <!-- 동작 : Controller에 해당 페이지 요청 -> 컨트롤러 에서 해당 페이지 요청, 비밀번호 확인 후 일치하면 수행 후 -> Controller에서 요청한 JSP 페이지로 이동  -->
		<div class="size_long text_long">회 원 탈 퇴</div>
		<div class="size_long" id="divpasswd"><input type="password" class="text_login" id="passwd" name="passwd" maxlength="15" placeholder="비밀번호"> </div>
		<div id="remove_btn_remove" class="btn_long"><a href="#" >회 원 탈 퇴</a></div>
	</form>
</div>
</body>

</html>