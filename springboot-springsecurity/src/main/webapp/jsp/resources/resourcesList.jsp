<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<style type="text/css">
	#button{
	    background-color: #4CAF50; /* Green */
	    border: none;
	    color: white;
	    padding: 5px 16px;
	    text-align: center;
	    text-decoration: none;
	    display: inline-block;
	    font-size: 1.0em;
	    margin: 2px 1px;
	    cursor: pointer;
		border-radius: 12px;
    }
	.right{
		color: blue; 
		text-decoration:none;
		font-size: 1.0em;
		display: block;
		height: 16px;
		float: right;
		margin-top: 0;
		position: absolute;
		right: 180px;top: 0;
	}
	.left{
		color: blue; 
		text-decoration:none;
		font-size: 1.0em;
		display: block;
		height: 16px;
		float: right;
		margin-top: 0;
		position: absolute;
		right: 100px;top: 0;
	}
	.add{
		background-color: #4CAF50; /* Green */
	    border: none;
	    color: white;
	    padding: 5px 16px;
	    text-align: center;
	    text-decoration: none;
	    display: inline-block;
	    font-size: 1.0em;
	    margin: 2px 1px;
	    cursor: pointer;
		border-radius: 12px;
		height: 30px;
		float: left;
		margin-top: 0;
		position: absolute;
		left: 20px;top: 10;
	}
	.table{
		color: DarkSlateGray; 
		text-decoration:none;
		display: block;
		margin-top: 0;
		position: absolute;
		right: 0px;top: 50px;
	}
	.paging{
		color: DarkSlateGray; 
		text-decoration:none;
		display: block;
		margin-top: 0;
		float: left;
		position: absolute;
		left: 30%;
	}    
    .window{ 
	    width:400px; 
	    background-color:gray; 
	    position:absolute; 
	    padding:5px; 
	    margin:10px; 
	    display:none;
	    left:40%;top:0px
    }
    .content{ 
	    height:300px; 
	    background-color:white; 
	    font-size:16px; 
	    overflow:auto; 
    } 
    .title{ 
        padding:2px; 
        color:white; 
        font-size:15px;
    } 
	.title img{ 
        float:right; 
    }  
	
</style>
<body>
	<security:authorize buttonMethod="get" buttonUrl="/resources/**">
		<form id="formid">
		</form>
		<form action="${pageContext.request.contextPath}/resources/addOrUpdatePage/add">
			<input type="submit" value="添加权限" class="add" />
		</form>	
		<div class="table">
			<table class="imagetable" style="font-size: 15px;" border="1px" cellspacing="0px">
				<tr>
					<th width="1%">编号</th>
					<th width="1%">权限</th>
					<th width="1%">路径</th>
					<th width="1%">res-key</th>
					<th width="1%">类型</th>
					<th width="1%">操作</th>
				</tr>
				<tbody id="resources"></tbody>
			</table>			
			<div class="paging">
				总记录数：<span id="pageSizeCountId"></span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				总页数：<span id="pageCountId"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				当前页：<span id="pageId"></span>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="init(1)">首页</a>
				<a href="javascript:void(0)" onclick="init(2)" id="shang">上页</a>
				<a href="javascript:void(0)" onclick="init(3)" id="xia">下页</a>
				<a href="javascript:void(0)" onclick="init(4)">尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="pageSizeId" onchange="init(5)">
					<option value="5">5</option>
					<option value="10">10</option>
					<option value="15">15</option>
					<option value="20">20</option>
				</select>
			</div>
		</div>
	</security:authorize>
</body>
<script src="/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
		/* 页面加载完出现 */
		$(document).ready(function(){
			init();
		});
		
		function init(mark){
			var pageSize = "";
			var page = "";	
			if(mark == 1)
			{
				pageSize = $("#pageSizeId").val();
				page = 1;
			}else if(mark == 2)
			{
				pageSize = $("#pageSizeId").val();
				var pageCount = $("#pageId").text();
				page = (parseInt(pageCount)-1);
			}else if(mark == 3)
			{
				pageSize = $("#pageSizeId").val();
				var pageCount = $("#pageId").text();
				page = (parseInt(pageCount)+1);
			}else if(mark == 4)
			{
				pageSize = $("#pageSizeId").val();
				page = $("#pageCountId").text();
			}else if(mark == 5)
			{
				pageSize = $("#pageSizeId").val();
				page = 1;
			}
			$.ajax({
				type: "get",  // 请求方式(post或get)
				async:false,  //默认true(异步请求),设置为false(同步请求)
				url:"<%=request.getContextPath() %>/resources/resources",
				scriptCharset: 'utf-8',
				data:{pageSize:pageSize,page:page},
				dataType:'json',
				success:function(resourcesJson){
					$("#resources").empty();
					var list=resourcesJson.t;
					var resources = "";
					$("#pageSizeCountId").text(resourcesJson.pageSizeCount);
					$("#pageCountId").text(resourcesJson.pageCount);
					$("#pageId").text(resourcesJson.page);
					$("#pageSizeId").val(resourcesJson.pageSize);
				 	if(resourcesJson.page == 1)
					{
						$("#shang").hide();
					}else
					{
						$("#shang").show();
					}
					if(resourcesJson.page == resourcesJson.pageCount)
					{
						$("#xia").hide();
					}
					else
					{
						$("#xia").show();
					} 
					for(var i in list){
						var type = "";
						if(list[i].type == 1){
							type = "菜单";
						} else {
							type = "按钮";
						}
						
						resources += "<tr><td><center>"+list[i].id+"</center></td>"+
					 			"<td><center>"+list[i].name+"</center></td>"+
					 			"<td><center>"+list[i].resUrl+"</center></td></center></td>"+
					 			"<td><center>"+list[i].resKey+"</center></td></center></td>"+
					 			"<td><center>"+type+"</center></td></center></td>"+
						 		"<td><center><input type='button' id='button' onclick='resourcesUpdate("+list[i].id+")' value='修改'>&nbsp;"+
						 		"<input type='button' id='button' onclick='resourcesDelete("+list[i].id+")' value='删除'>&nbsp;</td></tr>";
					}
					$("#resources").append(resources);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
		        }
			});
		}
		
		function resourcesUpdate(id){
			$('#formid').attr('action',"<%=request.getContextPath() %>/resources/addOrUpdatePage/"+id);
			document.getElementById("formid").submit();
		}
		
		function resourcesDelete(id){
			if (confirm("确认要删除？")) {
				$.ajax({
					type: "post",  // 请求方式(post或get)
					async:false,  //默认true(异步请求),设置为false(同步请求)
					url:"<%=request.getContextPath() %>/resources/resources",
					scriptCharset: 'utf-8',
					data:{
						"id":id,
						_method : "DELETE",
					},
					dataType:'json',
					success:function(resourcesJson){
						if(resourcesJson != null){
							alert("删除成功");
							init();
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						alert(XMLHttpRequest.status);
						alert(XMLHttpRequest.readyState);
						alert(textStatus);
			        }
				});
			}
		}
		
</script>
</html>