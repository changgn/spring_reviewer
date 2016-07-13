<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE>
<html>
<head>
<title>신고중</title>
</head>
<body>
	<c:if test="${reportok == 'reportok'}">
		<script>
			alert("게시글이 신고 되었습니다.");
			history.go(-1);
		</script>
	</c:if>
	<c:if test="${reportok == 'reportfalse'}">
		<% response.sendRedirect("main/main.do?sort=all"); %>
	</c:if>
	<c:if test="${reportn == 'reportn'}">
		<script>
			alert("이미 신고한 게시글 입니다.");
			history.go(-1);
		</script>
	</c:if>
</body>
</html>