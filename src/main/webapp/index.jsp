<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<!-- <a href="hello.do">/hello.do</a><br>
	<a href="main/main.do">/main/main.do</a><br> -->
	<%-- <% response.sendRedirect("hello.do"); %> --%>
	<c:redirect url="main.do"/>
</body>
</html>