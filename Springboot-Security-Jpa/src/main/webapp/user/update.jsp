<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript">
function check() {
    var pass = $("#pass").val().replace(/\s+/g,"");
    var pass1 = $("#pass1").val().replace(/\s+/g,"");
    var password = $("#password").val().replace(/\s+/g,"");
    if (null==pass || ''==pass) {
         alert("请输入旧密码!");
    } else if (null==pass1 || ''==pass1) {
        alert("请输入新密码!");
        $("#password").empty();
        $("#pass").empty();
        $("#pass1").empty();
    } else if (null==password || ''==password) {
        alert("请确认密码!");
    } else if (password != pass1) {
        alert("密码不正确!");
        $("#password").empty();
        $("#pass").empty();
        $("#pass1").empty();
    } else {
    	$("#password").val(password);
        $("#pass").val(pass);
        document.getElementById("formid").submit();
    }
}
</script>
<body>
    <form:form id="formid" action="${pageContext.request.contextPath }/user/a" method="post" modelAttribute="user" >
       <form:hidden path="id" />
       <form:hidden path="username" />
       <input type="hidden" name="_method" value="PUT">                 
                        旧密码:<input type="password" id="pass" name="pass" />
                        新密码:<form:input path="password" type="password" />
                        确认密码:<input id="pass1" name="pass1" type="password" />
       <input id="size" name="size" value="${page.size }" type="hidden" />
       <input id="page" name="page" value="${page.page }" type="hidden" />
       <input id="uId" name="uId" value="${page.t.uId }" type="hidden" />
       <input id="uName" name="uName" value="${page.t.uName }" type="hidden" />
       <input id="uEnable" name="uEnable" value="${page.t.uEnable }" type="hidden" />
       <input type="button" id="sbt" value="Submit" onclick="check()">
    </form:form>
</body>
</html>