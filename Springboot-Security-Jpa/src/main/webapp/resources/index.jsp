<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>
<!DOCTYPE html>
<html>
    <head>
        <title>权限管理</title>
    </head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/paging.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/paging.js"></script>
    <script type="text/javascript">
    $(function(){
        var page = "${page}"; // 获取所有分页、搜索条件
        if(page != ""){//不等于空满足条件
            $("#page").val("${page.page}");
            $("#size").val("${page.size}");
            $("#rName").val("${page.t.rName }");
            $("#rMethod").val("${page.t.rMethod }");
            tableData();
        }
        
        tableData();//调用方法查询数据 
        $("#searchs").click(function(){//当点击搜索按钮就进行搜索
            $("#page").val(1);//默认第一页
            tableData();//查询出搜索的数据
        });
        
        //id顺序或id倒叙
        /*$("#asc,#desc").click(function(){
            var id = $(this).attr("id");
            var pageSize = $("#pageSize").val();
            var search = $("#search").val();
            if(id == "asc"){
                var order = $("#order").val();
                if(order != "0"){
                    $("#order").val(0);//0为顺序
                    tableData();
                }
            }else{
                var order = $("#order").val();
                if(order != "1"){
                    $("#order").val(1);//1为倒序
                    tableData();
                }
            }
        });*/
        
    });
  //跳转修改页面
    function update(data){
        $("#form").attr("action","${pageContext.request.contextPath }/resources/updates/"+data).submit();//修改action的值并提交form表单
    }

    //删除权限
    function deletes(data){
        var id = $(data).parent("td").siblings("td:first").text();
     // 作用域问题,要写在delete前面
        if(confirm("是否删除!")){
	        $.ajax({//用ajax静态访问servlet
	            type : "POST",
	            url : "${pageContext.request.contextPath }/resources/a",
	            dataType:"json",
	            async : false,//设置为同步请求
	            data : {
	            	"id" : id,
	                "_method":"DELETE"
	            },
	            success : function(data) {//成功则调用函数tableData添加数据到页面
	                if($("#page").val() == $("#pageCount").text() && Math.ceil(($("#pageSizeCount").text()-1)/$("#size").val()) != $("#page").val()){//  向上取整（如果总记录数-1除以当前条数）   != 需要跳转的页面
	                    $("#page").val($("#page").val()-1);
	                }
	                tableData();
	            },
	            error : function(XMLHttpRequest, textStatus, errorThrown) {
	                 alert("错误: "+XMLHttpRequest.status);
	            }
	        });
        }
    }
    function tableData(){
        $("#tables,#paging").empty();//清空表的值和页码数
        var form = $('form').serializeArray();//获取表单所有值的json格式
        $.ajax({//用ajax静态访问servlet
            type : "GET",
            url : "${pageContext.request.contextPath }/resources/a",
            dataType:"json",
            async : false,//设置为同步请求
            data : form,
            success : function(data) {//成功则调用函数tableData添加数据到页面
            	var list = data;
            	if(list.pt != "" ){
                    if($("#wu").text() == "无数据"){//如果无数据则满足条件(也就是只报一次无数据)
                        $("#wu").remove();
                    }
                    var enable = "";
                    var en = "";
                    for ( var i in list.pt) {//循环出数据的数据添加到table表中
                        $("#tables").append("<tr align='center'>"
                                +"<td>"+list.pt[i].id+"</td>"
                                +"<td>"+list.pt[i].name+"</td>"
                                +"<td>"+list.pt[i].resKey+"</td>"
                                +"<td>"+list.pt[i].resMethod+"</td>"
                                +"<td>"+list.pt[i].resUrl+"</td>"
                                +"<security:authorize buttonUrl='/resources/a' buttonMethod='PUT,DELETE,ALL'><td>"
                                +"<security:authorize buttonUrl='/resources/a' buttonMethod='PUT,ALL'><input type='button' name='update' class='updatebutton' value='修改' onclick='update("+list.pt[i].id+");'/></security:authorize>"
                                +"<security:authorize buttonUrl='/resources/a' buttonMethod='DELETE,ALL'><input type='button' onclick='deletes(this)' style='background-color:#FA5858;border: 1px solid #FA5858' class='updatebutton' value='删除' /></security:authorize>"
                                +"</td></security:authorize>"
                                +"</tr>");
                        enable = "";
                        en = "";
                    }
                    //参数依次为 总页数，当前页数，总条数，当前大小，页码数，分页所放的ul的ID的值 ，搜索框的值
                    myFunction(list.pageCount, list.page, list.pageSizeCount, list.size, 3, "paging", list.search);
                    var pageNum = $("#page").val();//当点击按钮页数，id为pageNumber就会获得点击的页数，用变量接住页数的值
                    $("ul button").each(function(){//匹配ul下的button的值
                        if(pageNum == $(this).text()){//当页数和该对象的文本值相等时
                            $(this).attr("style","color: #2eccfa;background: #dddddd;border: 1px solid #dddddd;");//就改变该按钮的样式
                            $(this).attr("disabled","disabled");
                        }
                    });
                    // 查询所有请求方式
                    $.ajax({
                        type:"get", //请求方式     对应form的  method请求
                        url:"${pageContext.request.contextPath }/resources/method", //请求路径  对应 form的action路径
                        cache: false,  //是否缓存，false代表拒绝缓存
                        dataType: "json",   //返回值类型 
                        success:function(data){
                        	var rm = data;
                            var ee = $("#rMethod").val();
                            $("#rMethod").empty();
                            for (var i in rm) {
                                $("#rMethod").append("<option value = '"+rm[i].name+"'>"+rm[i].name+"</option>");
                            }
                            var numbers = $("#rMethod").find("option"); //获取select下拉框的所有值
                            for (var j = 1; j < numbers.length; j++) {
                                if ($(numbers[j]).val() == ee) {
                                    $(numbers[j]).attr("selected", "selected");
                                }
                            }
                        }
                    });
                }else{
                    if($("#wu").text() == ""){//如果无数据则满足条件(也就是只报一次无数据)
                        $("#myTable").after("<h2 id='wu'>无数据</h2>");
                    } 
                }
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                 alert("错误: "+XMLHttpRequest.status);
            }
        });
    }
    
    </script>
    <body style="background-color:#FFEBCD;">
        <div align="center">
            <!-- <input id="dorder" name="order" type="hidden" value="1"/> --><!-- 默认排序倒序 -->
        <a href="<c:url value="/logout" />">Logout</a>
        <security:authorize buttonUrl="/user/b" buttonMethod="GET,ALL">
            <p>
                <a href="${pageContext.request.contextPath }/user/b">用户管理</a>
            </p>
        </security:authorize>
        <security:authorize buttonUrl="/role/b" buttonMethod="GET,ALL">
            <p>
                <a href="${pageContext.request.contextPath }/role/b">角色管理</a>
            </p>
        </security:authorize>
        <security:authorize buttonUrl="/resources/b" buttonMethod="GET,ALL">
            <p>
                <a href="${pageContext.request.contextPath }/resources/b">权限管理</a>
            </p>
        </security:authorize>
        <form action="" id="form">
            <input id="size" name="size" type="hidden" value="5"/><!-- 默认每页3条 -->
            <input id="page" name="page" type="hidden" value="1"/><!-- 默认第一页 -->
            <p>
                name:<input id="rName" name="rName" type="text">&nbsp;&nbsp;
                method:<select class="form-control" id="rMethod" name="rMethod">
                      
              </select>&nbsp;&nbsp;
                <input id="searchs" type="button" value="确定">
                <input type="reset" value="清空">&nbsp;&nbsp;<br />
                <security:authorize buttonUrl='/resources/a' buttonMethod='POST,ALL'>
                    <a href="${pageContext.request.contextPath }/resources/adds">Add</a>
                </security:authorize>
            </p>
        </form>
                <!-- <button type="button" id="asc">id顺序</button>
                <button type="button" id="desc">id倒序</button> -->
        <table id="myTable" border="1" cellpadding="10" cellspacing="0" style="border-collapse: collapse;">
            <!-- 表头 -->
            <thead>
                <tr align="center">
                    <td>Id</td>
                    <td>Name</td>
                    <td>Key</td>
                    <td>Method</td>
                    <td>Url</td>
                    <security:authorize buttonUrl='/resources/a' buttonMethod='PUT,DELETE,ALL'>
                        <td>操作</td>
                    </security:authorize>
                </tr>
            </thead>
            
            <!-- 主体 -->
            <tbody id="tables">
                
            </tbody>
            
            <!-- 页脚 -->
            <tfoot>
            </tfoot>
        </table>
        <div class="pagination">
            <ul id="paging">
            </ul>
        </div>
    </div>
    </body>
</html>