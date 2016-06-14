<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<title>회원가입 확인</title>
<style>
.join_message {
	margin: 0 auto;
	margin-top: 200px;
	
}
</style>
</head>
<body>

<c:if test="${smessage!=null}">
	<div class="join_message size_long text_long">${smessage}</div><br>
	2초 후 로그인 페이지로 이동합니다.
	<meta http-equiv="Refresh" content="2; url=/logon/login.do">
</c:if>
<c:if test="${fmessage!=null}">
	<div class="join_message size_long text_long">
		${fmessage}
	</div>
	<c:redirect url="/logon/login.do"></c:redirect>
</c:if>
</body>
</html>


