<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限添加</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript">
function check() {//.replace(/\s+/g,"")
    var name = $("#name").val().replace(/\s+/g,"");
    var parentId = $("#parentId").val().replace(/\s+/g,"");
    var resUrl = $("#resUrl").val().replace(/\s+/g,"");
    var resKey = $("#resKey").val().replace(/\s+/g,"");
    if (null==name || ''==name) {
         alert("请输入权限名称!");
    } else if (null==parentId || ''==parentId) {
    	alert("请输入父ID!");
    } else if (null == resUrl || '' == resUrl) {
    	alert("请输入Url!");
    } else if (null == resKey || '' == resKey) {
    	alert("请输入权限Key!");
    } else {
    	$("#name").val(name);
    	$("#parentId").val(parentId);
    	$("#resUrl").val(resUrl);
    	$("#resKey").val(resKey);
    	document.getElementById("formid").submit();
    }
}
</script>
<body>
    <div align="center">
	    <form:form id="formid" action="${pageContext.request.contextPath }/resources/a" method="post" modelAttribute="resources" >
	       <br />                 
	                        权限名称:<form:input path = "name" />
	       <br />                 
	                        父资源ID:<form:input path = "parentId" />
	       <br />                 
	                        资源路径:<form:input path = "resUrl" />
	       <br />
	                        请求方式:<form:select path="resMethod" items="${resourcesMethod}" itemLabel="name" itemValue="name"></form:select>
           <br />                
		          资源Key:<form:input path = "resKey" />
		   <br />                 
		           资源类型:<form:select path="type" items="${types}" itemLabel="name" itemValue="id"></form:select>
		   <br />                 
		          排序(未使用):<form:select path="sort" items="${sorts}" itemLabel="name" itemValue="id"></form:select>
           <br />
	       <input type="button" id="sbt" value="添加" onclick="check()">
	       <br />
	    </form:form>
    </div>
</body>
</html>