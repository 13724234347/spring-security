<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login page</title>
	</head>
	<body>
		<c:url var="loginUrl" value="/login" />
		<form action="${loginUrl}" method="post">
			<c:if test="${param.error != null}">
					<p>Invalid username and password.</p>
			</c:if>
			<c:if test="${param.logout != null}">
					<p>You have been logged out successfully.</p>
			</c:if>
				<input type="text"  id="username" name="ssoId" placeholder="Enter Username" required>
				<br/>
				<input type="password" id="password" name="password" placeholder="Enter Password" required>
				<br/><br/>
			<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
				<input type="submit" value="Login" />
		</form>
	</body>
</html>