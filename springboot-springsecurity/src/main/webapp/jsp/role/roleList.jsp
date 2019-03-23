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
	<security:authorize buttonMethod="get" buttonUrl="/role/**">
		<div class="window" id="center" > 
			<div id="title" class="title"><img src="/picture/off.jpg" alt="关闭" />权限添加</div> 
			<div id="content">
			</div> 
			<div align="center">
				<button id="addResources" onclick="addResources()">提交</button>
			</div>
	 	</div> 	
		<form id="formid">
		</form>
		<form action="${pageContext.request.contextPath}/role/addOrUpdatePage/add">
			<input type="submit" value="添加角色" class="add" />
		</form>	
		<div class="table">
			<table class="imagetable" style="font-size: 15px;" border="1px" cellspacing="0px">
				<tr>
					<th width="1%">编号</th>
					<th width="1%">角色</th>
					<th width="1%">role-Key</th>
					<th width="1%">权限</th>
					<th width="1%">操作</th>
				</tr>
				<tbody id="role"></tbody>
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
				url:"<%=request.getContextPath() %>/role/role",
				scriptCharset: 'utf-8',
				data:{pageSize:pageSize,page:page},
				dataType:'json',
				success:function(roleJson){
					$("#role").empty();
					var list=roleJson.t;
					var role = "";
					$("#pageSizeCountId").text(roleJson.pageSizeCount);
					$("#pageCountId").text(roleJson.pageCount);
					$("#pageId").text(roleJson.page);
					$("#pageSizeId").val(roleJson.pageSize);
				 	if(roleJson.page == 1)
					{
						$("#shang").hide();
					}else
					{
						$("#shang").show();
					}
					if(roleJson.page == roleJson.pageCount)
					{
						$("#xia").hide();
					}
					else
					{
						$("#xia").show();
					} 
					for(var i in list){
						role += "<tr><td><center>"+list[i].id+"</center></td>"+
					 			"<td><center>"+list[i].roleName+"</center></td>"+
					 			"<td><center>"+list[i].roleKey+"</center></td></center></td>"+
					 			"<td><center>";
				 				for(var a = 0;a<list[i].resourcesSet.length;a++){
				 					role +=list[i].resourcesSet[a].name;
				 					if((a+1) !=list[i].resourcesSet.length){
				 						role +=",";
				 					}
				 				};
				 				role += "</center></td>"+
							 		"<td><center><input type='button' id='button' onclick='roleUpdate("+list[i].id+")' value='修改'>&nbsp;"+
							 		"<input type='button' id='button' onclick='roleDelete("+list[i].id+")' value='删除'>&nbsp;"+
							 		"<input type='button' id='button' onclick='addResources_role("+list[i].id+")' value='赋权'>&nbsp;"+
							 		"</center></td></tr>";
					}
					$("#role").append(role);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
		        }
			});
		}
		
		function roleUpdate(id){
			$('#formid').attr('action',"<%=request.getContextPath() %>/role/addOrUpdatePage/"+id);
			document.getElementById("formid").submit();
		}
		
		function roleDelete(id){
			if (confirm("确认要删除？")) {
				$.ajax({
					type: "post",  // 请求方式(post或get)
					async:false,  //默认true(异步请求),设置为false(同步请求)
					url:"<%=request.getContextPath() %>/role/role",
					scriptCharset: 'utf-8',
					data:{
						"id":id,
						_method : "DELETE",
					},
					dataType:'json',
					success:function(roleJson){
						if(roleJson != null){
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
		
		function addResources_role(id){
            popCenterWindow();
            addEcho(id);
            
		};
		
		function addEcho(id){
			$.ajax({
				type: "get",  // 请求方式(post或get)
				async:false,  //默认true(异步请求),设置为false(同步请求)
				url:"<%=request.getContextPath() %>/role/addEcho",
				scriptCharset: 'utf-8',
				data:{
					"id":id,
				},
				dataType:'json',
				success:function(resourcesList){
					$("#content").empty();
					var resources = "";
					for(var i in resourcesList){
						if(resourcesList[i].sentence == "1"){
							resources +="<input type='checkbox' value="+resourcesList[i].id+" name='resources' checked='checked'>"+resourcesList[i].name+",<br/>";
						} else if(resourcesList[i].sentence == "0"){
							resources +="<input type='checkbox' value="+resourcesList[i].id+" name='resources'>"+resourcesList[i].name+",<br/>";
						}						
					}
					$("#addResources").val(id);
					$("#content").append(resources);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
		        }
			});				
			
			
		}
		
		
		//关闭弹出层
		function closeWindow(){ 
			$(".title img").click(function(){ 
		        $(this).parent().parent().hide("slow"); 
		    }); 
		} 
		//定义弹出居中窗口的方法 
	    function popCenterWindow(){ 
	    	specifications(); 
	        //计算弹出窗口的左上角Y的偏移量 
		    var popY=(windowHeight-popHeight)/2; 
		    var popX=(windowWidth-popWidth)/2; 
		    //alert('jihua.cnblogs.com'); 
		    //设定窗口的位置 
		    $("#center").css("top",popY).css("left",popX).slideToggle("slow");  
		    closeWindow(); 
	    } 
		//获取窗口的高度 
		var windowHeight; 
		//获取窗口的宽度 
		var windowWidth; 
		//获取弹窗的宽度 
		var popWidth; 
		//获取弹窗高度 
		var popHeight; 
		function specifications(){ 
		   windowHeight=$(window).height(); 
		   windowWidth=$(window).width(); 
		   popHeight=$(".window").height(); 
		   popWidth=$(".window").width(); 
		} 
		
		function addResources(){
			var obj = document.getElementsByName("resources");
			var check_val = [];
			var id = document.getElementById("addResources").value;
			for(k in obj){
		        if(obj[k].checked)
		            check_val.push(obj[k].value);
		    }
			$.ajax({
				type: "post",  // 请求方式(post或get)
				async:false,  //默认true(异步请求),设置为false(同步请求)
				url:"<%=request.getContextPath() %>/role/addResources",
				scriptCharset: 'utf-8',
				data:{
					"roleId":id,
					"resourcesId":check_val,
				},
				traditional: true,//这里设置为true
				success:function(resources){
					if(resources == "success"){
						window.location.reload();
					}
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