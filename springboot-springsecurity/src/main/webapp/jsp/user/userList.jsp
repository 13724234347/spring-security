<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
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
</head>
<body>	
	<security:authorize buttonMethod="get" buttonUrl="/user/**">
		<div class="window" id="center" > 
			<div id="title" class="title"><img src="/picture/off.jpg" alt="关闭" />角色添加</div> 
			<div id="content">
			</div> 
			<div align="center">
				<button id="addRole" onclick="addRole()">提交</button>
			</div>
	 	</div> 	
		<form id="formid">
		
		</form>
		<form action="${pageContext.request.contextPath}/user/addOrUpdatePage/add">
			<security:authorize buttonMethod="post" buttonUrl="/user/**">
				<input type="submit" value="添加用户" class="add" />
			</security:authorize>
		</form>
		<div class="table">
			<table class="imagetable" style="font-size: 15px;" border="1px" cellspacing="0px">
				<tr>
					<th width="1%">编号</th>
					<th width="1%">账号</th>
					<th width="1%">是否启用</th>
					<th width="1%">角色</th>
					<th width="1%">操作</th>
				</tr>
				<tbody id="user"></tbody>
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
				
			} else if (mark == 3)
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
				url:"<%=request.getContextPath() %>/user/user",
				scriptCharset: 'utf-8',
				data:{pageSize:pageSize,page:page},
				dataType:'json',
				success:function(userJson){
					$("#user").empty();
					var list=userJson.t;
					var user = "";
					$("#pageSizeCountId").text(userJson.pageSizeCount);
					$("#pageCountId").text(userJson.pageCount);
					$("#pageId").text(userJson.page);
					$("#pageSizeId").val(userJson.pageSize);
				 	if(userJson.page == 1)
					{
						$("#shang").hide();
					}else
					{
						$("#shang").show();
					}
					if(userJson.page == userJson.pageCount)
					{
						$("#xia").hide();
					}
					else
					{
						$("#xia").show();
					} 
					for(var i in list){
						var enable="";
						if(list[i].enable == 1){ //1代表该账号已启用
							enable = "已启用";
						} else {
							enable = "已停用";
						}
						user += "<tr><td><center>"+list[i].id+"</center></td>"+
					 			"<td><center>"+list[i].userAccount+"</center></td>"+
					 			"<td><center>"+enable+"</center></td></center></td>"+
					 			"<td><center>";
					 				for(var a = 0;a<list[i].role.length;a++){
					 					user +=list[i].role[a].roleName;
					 					if((a+1) !=list[i].role.length){
					 						user +=",";
					 					}
					 				};
					 				user += "</center></td>"+
							 		"<td><center><input type='button' id='button' onclick='userUpdate("+list[i].id+")' value='修改'>&nbsp;"+
							 		"<input type='button' id='button' onclick='userDelete("+list[i].id+")' value='删除'>&nbsp;"+
							 		"<input type='button' id='button' onclick='addRole_user("+list[i].id+")' value='添加角色'>&nbsp;"+
							 		"</center></td></tr>";
					}
					$("#user").append(user);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
		        }
			});
		}
		
		function userUpdate(id){
			$('#formid').attr('action',"<%=request.getContextPath() %>/user/addOrUpdatePage/"+id);
			document.getElementById("formid").submit();
		}
		
		function userDelete(id){
			if (confirm("确认要删除？")) {
				$.ajax({
					type: "post",  // 请求方式(post或get)
					async:false,  //默认true(异步请求),设置为false(同步请求)
					url:"<%=request.getContextPath() %>/user/user",
					scriptCharset: 'utf-8',
					data:{
						"id":id,
						_method : "DELETE",
					},
					dataType:'json',
					success:function(userJson){
						if(userJson != null){
							alert("删除成功");
							parent.location.reload();
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
		
		function addRole_user(id){
            popCenterWindow();
            addEcho(id);
            
		};
		
		function addEcho(id){
			$.ajax({
				type: "get",  // 请求方式(post或get)
				async:false,  //默认true(异步请求),设置为false(同步请求)
				url:"<%=request.getContextPath() %>/user/addEcho",
				scriptCharset: 'utf-8',
				data:{
					
					"id":id,
				},
				dataType:'json',
				success:function(roleList){
					$("#content").empty();
					var role = "";
					for(var i in roleList){
						if(roleList[i].sentence == "1"){
							role +="<input type='checkbox' value="+roleList[i].id+" name='role' checked='checked'>"+roleList[i].roleName+",<br/>";
						} else if(roleList[i].sentence == "0"){
							role +="<input type='checkbox' value="+roleList[i].id+" name='role'>"+roleList[i].roleName+",<br/>";
						}						
					}
					$("#addRole").val(id);
					$("#content").append(role);
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
		
		function addRole(){
			var obj = document.getElementsByName("role");
			var check_val = [];
			var id = document.getElementById("addRole").value;
			for(k in obj){
		        if(obj[k].checked)
		            check_val.push(obj[k].value);
		    }
			$.ajax({
				type: "post",  // 请求方式(post或get)
				async:false,  //默认true(异步请求),设置为false(同步请求)
				url:"<%=request.getContextPath() %>/user/addRole",
				scriptCharset: 'utf-8',
				data:{
					"userId":id,
					"roleId":check_val,
				},
				traditional: true,//这里设置为true
				success:function(role){
					if(role == "success"){
						parent.location.reload();
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