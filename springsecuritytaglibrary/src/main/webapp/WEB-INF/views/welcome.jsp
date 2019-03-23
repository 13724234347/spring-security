<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored="false" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Welcome page</title>
</head>
<body>
	<a href="<c:url value="/test" />">aaa</a>
	=================
	Dear <strong>${user}</strong>, Welcome to Home Page.
	<a href="<c:url value="/logout" />">Logout</a>

	<br/>
	<br/>
	<div>
		<label>View all information| This part is visible to Everyone</label>
	</div>

	<br/>
	<div>
		<%-- <!-- spring security 相关属性 -->
		===<sec:authentication property="principal.username"/>===
		
		<sec:authentication property="authorities" var="aa" scope="page"/>
		<c:forEach items="${aa}" var="authority">
		  ${authority.authority}
		</c:forEach> --%>

		<sec:authorize access="hasRole('ADMIN')">
			<label>只有admin权限才显示</label>
		</sec:authorize>
	</div>

	<br/>
	<div>
		<sec:authorize access="hasRole('USER')">
			<label><a href="#">Start backup</a>只有user权限才显示 </label>
		</sec:authorize>
		
		<sec:authorize access="hasRole('DBA')">
			<label>只有dba权限才显示</label>
		</sec:authorize>
	</div>
</html>