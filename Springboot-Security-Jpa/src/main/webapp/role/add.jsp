<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色添加</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript">
function check() {//.replace(/\s+/g,"")
    var roleDesc = $("#roleDesc").val().replace(/\s+/g,"");
    var roleKey = $("#roleKey").val().replace(/\s+/g,"");
    if (null==roleDesc || ''==roleDesc) {
         alert("请输入角色名称!");
    } else if (null==roleKey || ''==roleKey) {
    	alert("请输入角色key!");
    } else {
    	$("#roleDesc").val(roleDesc);
    	$("#roleKey").val(roleKey);
    	document.getElementById("formid").submit();
    }
}
</script>
<body>
    <div align="center">
	    <form:form id="formid" action="${pageContext.request.contextPath }/role/a" method="post" modelAttribute="role" >
	                        角  色  名  称  :<form:input path="roleDesc" />
	       <br />
	                        角  色  key:<form:input path="roleKey" />
	       <br />                
	       <input type="button" id="sbt" value="添加" onclick="check()">
	       <br />
	    </form:form>
    </div>
</body>
</html>