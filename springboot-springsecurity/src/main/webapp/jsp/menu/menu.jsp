<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<style>
	a{
		color: DarkSlateGray; 
		text-decoration:none;
	}
</style>
<body>
<security:authorize buttonMethod="get" buttonUrl="/index.do">
	<p id="menu"></p>
</security:authorize>
</body>
<script src="/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
	/* 页面加载完出现 */
	$(document).ready(function(){
		init();
	});
	function init(){
		$.ajax({
			type: "get",  // 请求方式(post或get)
			async:false,  //默认true(异步请求),设置为false(同步请求)
			url:"<%=request.getContextPath() %>/resources/queryMenu",
			scriptCharset: 'utf-8',
			dataType:'json',
			success:function(list){
				$("#menu").empty();
				var menu = "";
				for(var i in list){
					menu +="<security:authorize buttonMethod="+list[i].method+" buttonUrl="+list[i].resUrl+"><a href='${pageContext.request.contextPath}"+list[i].resUrl+"' target='right'>"+list[i].name+"</a></security:authorize><br/><br/>";
				}
				$("#menu").append(menu);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
	        }
		});
	}
</script>
</html>