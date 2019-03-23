<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户添加</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript">
function check() {//.replace(/\s+/g,"")
    var username = $("#username").val().replace(/\s+/g,"");
    var password = $("#password").val().replace(/\s+/g,"");
    var pass = $("#pass").val().replace(/\s+/g,"");
    if (null==username || ''==username) {
         alert("请输入用户名!");
         $("#username").empty();
         $("#password").empty();
         $("#pass").empty();
    } else if (null==password || ''==password) {
    	alert("请输入密码!");
    	$("#password").empty();
        $("#pass").empty();
    } else if (null == pass || '' == pass) {
    	alert("请确认密码!");
    	$("#password").empty();
        $("#pass").empty();
    } else if (password != pass) {
    	alert("密码输入错误!");
    	$("#password").empty();
        $("#pass").empty();
    } else {
    	$("#username").val(username);
    	$("#password").val(password);
    	document.getElementById("formid").submit();
    }
}
</script>
<body>
    <div align="center">
	    <form:form id="formid" action="${pageContext.request.contextPath }/user/a" method="post" modelAttribute="user" >
	                        用  户  名:<form:input path="username" />
	       <br />
	                        密        码:<form:input path="password" type="password" />
	       <br />                
	                        确认密码:<input type="password" id="pass" name="pass">                 
	       <br />
	       <input type="button" id="sbt" value="添加" onclick="check()">
	       <br />
	    </form:form>
    </div>
</body>
</html>