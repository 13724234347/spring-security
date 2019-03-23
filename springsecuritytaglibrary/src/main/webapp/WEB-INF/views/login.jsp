<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login page</title>
	</head>
	<body>
		<c:url var="loginUrl" value="/login" />
		<form action="${loginUrl}" method="post" class="form-horizontal">
			<c:if test="${param.error != null}">
					<p>Invalid username and password.</p>
			</c:if>
			<c:if test="${param.logout != null}">
					<p>You have been logged out successfully.</p>
			</c:if>
				<div align="center">
					<input type="text" class="form-control" id="username" name="ssoId" placeholder="Enter Username" required>
					<br/><br/>
					<input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
					<br/><br/>
					<input type="hidden" name="${_csrf.parameterName}" 	value="${_csrf.token}" />
					<input type="submit" value="Login">
				</div>
		</form>
	</body>
</html>