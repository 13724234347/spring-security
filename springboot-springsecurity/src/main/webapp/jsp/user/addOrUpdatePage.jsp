<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<style type="text/css">
#userAccount,#userPassword {
		font-size:1.0em; /* 设置输入框中字体的大小 */
		height:2.0em; /* 设置输入框的高度 */
		border-radius:10px; /* 设置输入框的圆角的大小 */
		border:1px solid #c8cccf;/* 设置输入框边框的粗细和颜色 */
		color:#986655; /* 设置输入框中文字的颜色 */
		outline:0;/*  将输入框点击的时候出现的边框去掉 */
		text-align:center; /*设置输入框中文字的位置 */
		display:block; /* 将输入框设置为块级元素 */
    }
    .button{
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
        background-color: write;
	}
	 #p{
       	position: absolute;     /* 相对定位或绝对定位均可 */
        top: 22.5%;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: write;
	}
</style>
<body>
	<security:authorize buttonMethod="put" buttonUrl="/role/**">
		 <div>
		 	<p id="p"></p>
		 </div>
		<c:if test="${value=='add'}">
			 <div id="div">
				<form:form action="${pageContext.request.contextPath}/user/user" method="post" modelAttribute="user">
					<table>
						<tr>
							<td>&nbsp;&nbsp;&nbsp;User:</td>
							<td><form:input path="userAccount" /></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><form:password path="userPassword"/></td>
						</tr>
						<tr>
							<td colspan="2">
								<div align="center">
									<input type="submit" value="提交" class="button" />&nbsp;&nbsp;&nbsp;&nbsp;<input
										type="reset" value="重置" class="button" />
								</div>
							</td>
						</tr>
					</table>
				</form:form>
			</div>
		</c:if>
		<c:if test="${value=='update'}">
			 <div id="div">
				<form:form action="${pageContext.request.contextPath}/user/user" method="post" modelAttribute="user">
					<input type="hidden" name="_method" value="put"/>
					<table>
						<form:hidden path="id"/>
						<tr>
							<td>&nbsp;&nbsp;&nbsp;User:</td>
							<td><form:input path="userAccount" /></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><form:password path="userPassword"/></td>
						</tr>
						<tr>
							<td>enable:</td>
							<td><form:radiobuttons path="enable"  items="${enable}" /></td>
						</tr>
						<tr>
							<td colspan="2">
								<div align="center">
									<input type="submit" value="提交" class="button" />&nbsp;&nbsp;&nbsp;&nbsp;<input
										type="reset" value="重置" class="button" />
								</div>
							</td>
						</tr>
					</table>
				</form:form>
			</div>
		</c:if>
		<input type="hidden" id="hiddenId" value="${value}">
	</security:authorize>
</body>
<script src="/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
	/* 页面加载完出现 */
	$(document).ready(function(){
		var value = document.getElementById("hiddenId").value;
		var title = "";
		if(value=="add"){
			title = "<h2>欢迎来到用户添加页面</h2>";
		} else if(value=="update"){
			title = "<h2>欢迎来到用户修改页面</h2>";
		}
		$("#p").append(title);
	});
</script>

</html>