<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>회원가입 확인</title>
<style>
.join_message {
	margin: 0 auto;
	margin-top: 400px;
	
}
</style>
</head>
<body>

<c:if test="${smessage!=null}">
	<div class="join_message size_long text_long">${smessage}</div><br>
	2초 후 로그인 페이지로 이동합니다.
	<%-- <%response.sendRedirect("/reviewer/logon/logonForm.do"); %> --%>
	<meta http-equiv="Refresh" content="2;url=logon/login.do">
</c:if>
<c:if test="${fmesaage!=null}">
	<div class="join_message size_long text_long">
		${fmessage}
	</div>
	<%response.sendRedirect("join.do"); %>
</c:if>
</body>
</html>