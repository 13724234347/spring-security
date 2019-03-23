<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<style>
	.right{
		color: DarkSlateGray; 
		text-decoration:none;
		font-size: 1.0em;
		display: block;
		height: 16px;
		float: right;
		margin-top: 0;
		position: absolute;
		right: 20px;top: 15px;
	}
	.left{
		color: green; 
		text-decoration:none;
		display: block;
		height: 16px;
		float: left;
		margin-top: 0;
		position: absolute;
		left: 40%;top: 0;
	}
</style>
<body>
	<h2 class="left">欢迎来到spring-security系统1.0</h2>
	<a href="JavaScript:parent.window.location.href= '${pageContext.request.contextPath}/logout';" class="right" target="top"><span>退出登录</span></a>	
</body>
</html>