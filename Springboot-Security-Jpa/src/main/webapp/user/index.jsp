<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>
<!DOCTYPE html>
<html>
    <head>
        <title>用户管理</title>
    </head>
<style>

    #Authorized_Show {  
        display:none;  
        border:1em solid blue;
        height:50%;  
        width:24%;  
        position:absolute;/*让节点脱离文档流,我的理解就是,从页面上浮出来,不再按照文档其它内容布局*/  
        top:20%;/*节点脱离了文档流,如果设置位置需要用top和left,right,bottom定位*/  
        left:35%;  
        z-index:2;/*个人理解为层级关系,由于这个节点要在顶部显示,所以这个值比其余节点的都大*/  
        background: white;  
    } 
    
    #Authorized_Over {  
        width: 100%;  
        height: 100%;  
        opacity:0.3;/*设置背景色透明度,1为完全不透明,IE需要使用filter:alpha(opacity=80);*/  
        filter:alpha(opacity=80);  
        display: none;  
        position:absolute;  
        top:0;  
        left:0;  
        z-index:1;  
        background: silver;  
    }
    
    a{ text-decoration:none;}
    /* css注释： 鼠标经过热点文字被加下划线 */ 
    a:hover{ text-decoration:underline;}
    /* a 标签字体颜色为 黑色 */
    a{color:#000}
</style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/paging.css"/>
    
    <body style="background-color:#FFEBCD;">
        <div align="center">
            <!-- <input id="dorder" name="order" type="hidden" value="1"/> --><!-- 默认排序倒序 -->
        <p><a href="<c:url value="/logout" />">Logout</a></p>
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
                id:<input id="uId" name="uId" type="text">&nbsp;&nbsp;
                name:<input id="uName" name="uName" type="text">&nbsp;&nbsp;
                status:<select class="form-control" id="uEnable" name="uEnable">
                      
              </select>&nbsp;&nbsp;
                <input id="searchs" type="button" value="确定">
                <input type="reset" value="清空">&nbsp;&nbsp;<br />
                <security:authorize buttonUrl='/user/a' buttonMethod='POST,ALL'>
                    <a href="${pageContext.request.contextPath }/user/adds">Add</a>
                </security:authorize>
            </p>
        </form>
        <!-- 获取登录用户名 -->
        <%String names = request.getSession().getAttribute("names").toString();%>
        <%-- <input type="hidden" id="names" name="names" value="<%=names %> " /> --%>
        <b hidden id="names" ><%=names %></b>
                <!-- <button type="button" id="asc">id顺序</button>
                <button type="button" id="desc">id倒序</button> -->
        <table id="myTable" border="1" cellpadding="10" cellspacing="0" style="border-collapse: collapse;">
            <!-- 表头 -->
            <thead>
                <tr align="center">
                    <td>Id</td>
                    <td>Name</td>
                    <td>Status</td>
                    <security:authorize buttonUrl='/user/a' buttonMethod='DELETE,ALL'>
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
        <br />
        <br />
            <form action="${pageContext.request.contextPath }/user/userAuthorized2" id="AuthId" method="post">
                <div id="Authorized_Show">
                    <div align="center">
                        <p align="center"><h3>授权</h3></p>
                    </div>
                    <div id="ddiv">
                       <input type='checkbox' name='sc' id='sc' value='null' onchange='sel1()'  lay-skin='primary' />全选<br />
                    </div>
                    <input type="hidden" name="_method" value="PUT" />
                    <button>
                        <a href="javascript:user_hide()">&nbsp;Close&nbsp;</a>
                    </button>
                    <input type='submit' value='授权' />
                </div>
                <div id="Authorized_Over"></div>
            </form>
        </div>
    </body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/paging.js"></script>
    <script type="text/javascript">
    $(function(){
        var page = "${page}"; // 将所有分页、搜索条件复原
        if(page != ""){//不等于空满足条件
            $("#page").val("${page.page}");
            $("#size").val("${page.size}");
            $("#uId").val("${page.t.uId }");
            $("#uName").val("${page.t.uName }");
            $("#uEnable").val("${page.t.uEnable }");
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
        $("#form").attr("action","${pageContext.request.contextPath }/user/updates/"+data).submit();//修改action的值并提交form表单
    }

  //启用/禁用用户
    function updates(data){
        //var id = $(data).parent("td").siblings("td:first").text();
        $.ajax({//用ajax静态访问servlet
            url : "${pageContext.request.contextPath }/user/c",
            async : false,//设置为同步请求
            data : {
                "id" : data,
                "_method":"PUT"
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
  
    //删除用户
    function deletes(data){
        var id = $(data).parent("td").siblings("td:first").text();
     // 作用域问题,要写在delete前面
        if(confirm("是否删除!")){
            $.ajax({//用ajax静态访问servlet
                type : "POST",
                url : "${pageContext.request.contextPath }/user/a",
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
  //重置用户密码
    function resets(data){
        //var id = $(data).parent("td").siblings("td:first").text();
     // 作用域问题,要写在delete前面
        if(confirm("是否重置密码!")){
            $.ajax({//用ajax静态访问servlet
                type : "POST",
                url : "${pageContext.request.contextPath }/user/reset",
                dataType: "json",
                async : false,//设置为同步请求
                data : {
                    "id" : data,
                    "_method":"PUT"
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
        var names = $("#names").text();
        $.ajax({//用ajax静态访问servlet
            type : "GET",
            url : "${pageContext.request.contextPath }/user/a",
            dataType:"json",
            async : false,//设置为同步请求
            data : form,
            success : function(data) {//成功则调用函数tableData添加数据到页面
            	var ee = $("#uEnable").val();//加载搜索下拉框
            	$("#uEnable").html("<option value = ''>所有</option>"+"<option value = '0'>禁用</option>" + "<option value = '1'>启用</option>");
            	var numbers = $("#uEnable").find("option"); //获取select下拉框的所有值
            	for (var j = 1; j < numbers.length; j++) {
	            	if ($(numbers[j]).val() == ee) {
	            		$(numbers[j]).attr("selected", "selected");
	            	}
            	} 
            	if(data.pt != "" ){
                    if($("#wu").text() == "无数据"){//如果无数据则满足条件(也就是只报一次无数据)
                        $("#wu").remove();
                    }
                    var enable = "";
                    var en = "";
                    for ( var i in data.pt) {//循环出数据的数据添加到table表中
                        if (0 == data.pt[i].enable) {
                            enable = "禁用";
                            en = "启用";
                        } else {
                            enable = "启用";
                            en = "禁用";
                        }
                        if (names != data.pt[i].username) {
	                        $("#tables").append("<tr align='center'>"
	                                +"<td>"+data.pt[i].id+"</td>"
	                                +"<td>"+data.pt[i].username+"</td>"
	                                +"<td>"+enable+"</td>"
	                                +"<security:authorize buttonUrl='/user/a' buttonMethod='DELETE,ALL'><td>"
	                                +"<security:authorize buttonUrl='/user/a' buttonMethod='ALL'><input type='button' name='updates' class='updatebutton' value='"+en+"' onclick='updates("+data.pt[i].id+");'/></security:authorize>"
	                                +"<security:authorize buttonUrl='/user/a' buttonMethod='ALL'><input type='button' name='shows' class='updatebutton' value='授权' onclick='user_show("+data.pt[i].id+");'/></security:authorize>"
	                                +"<security:authorize buttonUrl='/user/a' buttonMethod='ALL'><input type='button' name='resets' class='updatebutton' value='重置密码' onclick='resets("+data.pt[i].id+");'/></security:authorize>"
	                                +"<security:authorize buttonUrl='/user/a' buttonMethod='DELETE,ALL'><input type='button' onclick='deletes(this)' style='background-color:#FA5858;border: 1px solid #FA5858' class='updatebutton' value='删除' /></security:authorize>"
	                                +"</td></security:authorize>"
	                                +"</tr>");
                        } else {
                        	$("#tables").append("<tr align='center'>"
                                    +"<td>"+data.pt[i].id+"</td>"
                                    +"<td>"+data.pt[i].username+"</td>"
                                    +"<td>"+enable+"</td>"
                                    +"<td>"
                                    +"<security:authorize buttonUrl='/user/a' buttonMethod='ALL'><input type='button' name='updates' class='updatebutton' value='"+en+"' onclick='updates("+data.pt[i].id+");'/></security:authorize>"
                                    +"<security:authorize buttonUrl='/user/a' buttonMethod='ALL'><input type='button' name='shows' class='updatebutton' value='授权' onclick='user_show("+data.pt[i].id+");'/></security:authorize>"
                                    +"<security:authorize buttonUrl='/user/a' buttonMethod='PUT,ALL'><input type='button' name='update' class='updatebutton' value='改密' onclick='update("+data.pt[i].id+");'/></security:authorize>"
                                    +"<security:authorize buttonUrl='/user/a' buttonMethod='DELETE,ALL'><input type='button' onclick='deletes(this)' style='background-color:#FA5858;border: 1px solid #FA5858' class='updatebutton' value='删除' /></security:authorize>"
                                    +"</td>"
                                    +"</tr>");
                        }
                        enable = "";
                        en = "";
                    }
                    //参数依次为 总页数，当前页数，总条数，当前大小，页码数，分页所放的ul的ID的值 ，搜索框的值
                    myFunction(data.pageCount, data.page, data.pageSizeCount, data.size, 3, "paging", data.search);
                    var pageNum = $("#page").val();//当点击按钮页数，id为pageNumber就会获得点击的页数，用变量接住页数的值
                    $("ul button").each(function(){//匹配ul下的button的值
                        if(pageNum == $(this).text()){//当页数和该对象的文本值相等时
                            $(this).attr("style","color: #2eccfa;background: #dddddd;border: 1px solid #dddddd;");//就改变该按钮的样式
                            $(this).attr("disabled","disabled");
                        }
                    });
                    // 查询所有角色(先存起来)
                    $.ajax({
                        type:"get", //请求方式     对应form的  method请求
                        url:"${pageContext.request.contextPath }/role/userAuthorized1", //请求路径  对应 form的action路径
                        cache: false,  //是否缓存，false代表拒绝缓存
                        dataType: "json",   //返回值类型 
                        success:function(data){
                            var list = data;
                            $("#ddiv").html("<input type='checkbox' name='sc' id='sc' value='null' onchange='sel1()'  lay-skin='primary' />全选<br />");
                            var html = "";
                            for(var i in list){
                                html += "<input type='checkbox' name='sel' value=" + list[i].id + " lay-skin='primary' />" + list[i].roleDesc + "&nbsp;&nbsp;&nbsp;&nbsp;<br />";
                            }
                            $("#ddiv").append(html);
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
  //全选/全不选
    function sel1(){
        var isCheck=$("#sc").is(':checked');  //获得全选复选框是否选中
        $("input[type='checkbox']").each(function() {  
            this.checked = isCheck;       //循环赋值给每个复选框是否选中
        });
    }
    var rshow = document.getElementById('Authorized_Show');
    var rohide = document.getElementById('Authorized_Over');
    // 显值授权
    function user_show(id){
        $.ajax({
            type: "get",  // 请求方式(post或get)
            async:false,  //默认true(异步请求),设置为false(同步请求)
            url:"${pageContext.request.contextPath }/role/userAuthorized", // 发送请求的地址
            scriptCharset: 'utf-8',
            dataType:"json",
            data: {"id" : id},
            success:function(role){
                //var userRole = eval("("+resource+")");
                // 清空所有选中属性,如果不清空就会导致点击下一个是查出来的角色也会有上一个用户中的角色
                $("input[type='checkbox']").each(function() { this.checked = false;  });
                //$("input[type='checkbox'").removeAttr('checked', 'checked');
                $("#ddiv").append("<input type='hidden' value='"+id+"' id='id' name='id'>");
                for(var i = 0;i < role.length;i++)//返回用户的权限
                {
                    //把用户查询出来的角色，与我们所有的角色复选框逐一进行比较，如果值相同就就进行选中。
                    $("input[name='sel']").each(function(){// 循环选中的checkbox（复选框）
                        var value = $(this).val(); // 获取复选框中的值(此处是角色id)
                        if(value == role[i])
                        {
                            $(this).prop("checked",true); // 如果相同就勾选上(默认值),因为先前清空了选中值
                        }
                    });
                }
            }
        });
        rshow.style.display = "block";
        rohide.style.display = "block";
    }
    function user_hide()
    {  
        rshow.style.display = "none";
        rohide.style.display = "none";
    }
    </script>
</html>