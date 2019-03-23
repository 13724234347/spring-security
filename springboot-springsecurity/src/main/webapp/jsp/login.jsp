<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>spring-security</title>
<style type="text/css">
	.username,.password {
		font-size:1.0em; /* 设置输入框中字体的大小 */
		height:2.0em; /* 设置输入框的高度 */
		border-radius:10px; /* 设置输入框的圆角的大小 */
		border:1px solid #c8cccf;/* 设置输入框边框的粗细和颜色 */
		color:#986655; /* 设置输入框中文字的颜色 */
		outline:0;/*  将输入框点击的时候出现的边框去掉 */
		text-align:center; /*设置输入框中文字的位置 */
		display:block; /* 将输入框设置为块级元素 */
	}
	.login{
		background-color: #4CAF50; /* Green */
		border: none;
		color: white;
		padding: 10px 32px;
		text-align: center;
		text-decoration: none;
		display: inline-block;
		font-size: 1.0em;
		margin: 4px 2px;
		cursor: pointer;
		border-radius: 12px;
	}

	#div{
		position: absolute;     /* 相对定位或绝对定位均可 */
		top: 40%;
		left: 50%;
		transform: translate(-50%, -50%);
		background-color:write;
	}
</style>
</head>
<body>	
	 <div id="div">
		<h2 align="center">spring-security</h2>
		<c:if test="${param.value=='error'}">
			<div class="error" align="center"><h2>用户名不存在或者密码错误</h2></div>
		</c:if>
		<c:if test="${param.logout=='logout'}">
			<div class="logout">You've been logged out successfully.</div>
		</c:if>
		<form action="${pageContext.request.contextPath}/spring_security_check" method="post">
			<table>
				<tr>
					<td>&nbsp;&nbsp;&nbsp;User:</td>
					<td><input type='text' name='username' class="username"  placeholder="请输入用户账号"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password' class="password"  placeholder="请输入用户密码"/></td>
				</tr>
				<tr align="center">
					<td colspan='2'><input name="login" class='login' type="submit" value="登入" /></td>
				</tr>
			</table>
		</form>
	</div> 
</body>
</html>