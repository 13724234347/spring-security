<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>
<!DOCTYPE html>
<html>
    <head>
        <title>角色管理</title>
<style>

    #Empowerment_Show {  
        display:none;  
        border:1em solid blue;
        height:60%;  
        width:20%;  
        position:absolute;/*让节点脱离文档流,我的理解就是,从页面上浮出来,不再按照文档其它内容布局*/  
        top:20%;/*节点脱离了文档流,如果设置位置需要用top和left,right,bottom定位*/  
        left:35%;  
        z-index:2;/*个人理解为层级关系,由于这个节点要在顶部显示,所以这个值比其余节点的都大*/  
        background: white;  
    } 
    
    #Empowerment_Over {  
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
    </head>
    
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
	                <security:authorize buttonUrl='/role/a' buttonMethod='POST,ALL'>
	                   <a href="${pageContext.request.contextPath }/role/adds">Add</a>
	                </security:authorize>
	            </p>
	        </form>
	                <!-- <button type="button" id="asc">id顺序</button>
	                <button type="button" id="desc">id倒序</button> -->
	        <table id="myTable" border="1" cellpadding="10" cellspacing="0" style="border-collapse: collapse;" width="50%">
	            <!-- 表头 -->
	            <thead>
	                <tr align="center">
	                    <td>Id</td>
	                    <td>Desc</td>
	                    <td>RoleKey</td>
	                    <security:authorize buttonUrl='/role/a' buttonMethod='PUT,DELETE,ALL'>
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
	        <form action="${pageContext.request.contextPath }/role/roleEmpowerment2" id="EmpoId" method="post">
	            <div id="Empowerment_Show">
	                <div align="center">
	                    <p align="center"><h3>赋权</h3></p>
	                </div>
	                <ul id="treeDemo" class="ztree" style="margin-top: 10px; width: 200px; height: 300px;">
                    </ul>
	                <div id="ddiv">
	                </div>
	            <input type="hidden" name="_method" value="PUT">
                <button>
                    <a href="javascript:role_hide()">&nbsp;Close&nbsp;</a>
                </button>
	            <input type='submit' value='赋权' onclick="EmpoId()" />
	            </div>
	            <div id="Empowerment_Over"></div>
	        </form>
	    </div>
    </body>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/paging.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/paging.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/demo.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript">
    
      // 创建带有复选框的树
      function createTree(url, treeId) {
    	    var zTree; //用于保存创建的树节点
    	    var setting = { //设置
    	        check: {
    	            enable: true
    	        },
    	        view: {
    	            showLine: true, //显示辅助线
    	            dblClickExpand: true,
    	        },
    	        data: {
    	            simpleData: {
    	                enable: true,
    	                idKey: "id",
    	                pIdKey: "pid",
    	                rootPId: 0
    	            }
    	        }
    	    };
    	    $.ajax({ //请求数据,创建树
    	        type: 'GET',
    	        url: url,
    	        dataType: "json", //返回的结果为json  
    	        success: function(data) {
    	        	zTree = $.fn.zTree.init($(treeId), setting, data); //创建树
    	        },
    	        error: function(data) {
    	            alert("创建树失败!");
    	        }
    	    });
    	}
      // 赋权
      function EmpoId() {
    	    var checkedObjs = zTree.getCheckedNodes(true);  //获取所有选中的节点
			$("#EmpoId input[name='id']").remove();
			for(var i=0;i<checkedObjs.length;i++) {
			    var obj = checkedObjs[i];
			    if(obj.id) {
			        $("#EmpoId").append("<input type='hidden' name='id' value='"+obj.id+"' >");  //在form里增加input，将字符串数组传到后台
			    }
			}
      }
    $(function(){
        var page = "${page}"; // 获取分页搜索条件
        if(page != ""){//不等于空满足条件
            $("#page").val("${page.page}");
            $("#size").val("${page.size}");
            tableData();
        }
        
        tableData();//调用方法查询数据 
        /* $("#searchs").click(function(){//当点击搜索按钮就进行搜索
            $("#page").val(1);//默认第一页
            tableData();//查询出搜索的数据
        }); */
        
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
        $("#form").attr("action","${pageContext.request.contextPath }/role/updates/"+data).submit();//修改action的值并提交form表单
    }
  
    //删除角色
    function deletes(data){
        var id = $(data).parent("td").siblings("td:first").text();
     // 作用域问题,要写在delete前面
        if(confirm("是否删除!")){
            $.ajax({//用ajax静态访问servlet
                type : "POST",
                url : "${pageContext.request.contextPath }/role/a",
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
    // 默认加载，查询所有(分页)
    function tableData(){
        $("#tables,#paging").empty();//清空表的值和页码数
        var form = $('form').serializeArray();//获取表单所有值的json格式
        $.ajax({//用ajax静态访问servlet
            type : "GET",
            url : "${pageContext.request.contextPath }/role/a",
            dataType:"json",
            async : false,//设置为同步请求
            data : form,
            success : function(data) {//成功则调用函数tableData添加数据到页面
                if(data.pt != "" ){
                    if($("#wu").text() == "无数据"){//如果无数据则满足条件(也就是只报一次无数据)
                        $("#wu").remove();
                    }
                    for ( var i in data.pt) {//循环出数据的数据添加到table表中
                        $("#tables").append("<tr align='center'>"
                                +"<td>"+data.pt[i].id+"</td>"
                                +"<td>"+data.pt[i].roleDesc+"</td>"
                                +"<td>"+data.pt[i].roleKey+"</td>"
                                +"<security:authorize buttonUrl='/role/a' buttonMethod='PUT,DELETE,ALL'><td>"
                                +"<security:authorize buttonUrl='/role/a' buttonMethod='ALL'><input type='button' name='shows' class='updatebutton' value='赋权' onclick='role_show(" + data.pt[i].id + ");'/></security:authorize>"
                                +"<security:authorize buttonUrl='/role/a' buttonMethod='PUT,ALL'><input type='button' name='update' class='updatebutton' value='修改' onclick='update("+data.pt[i].id+");'/></security:authorize>"
                                +"<security:authorize buttonUrl='/role/a' buttonMethod='DELETE,ALL'><input type='button' onclick='deletes(this)' style='background-color:#FA5858;border: 1px solid #FA5858' class='updatebutton' value='删除' /></security:authorize>"
                                +"</td></security:authorize>"
                                +"</tr>");
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
                    // 保存所有权限
                    // 创建带有复选框的树
                    createTree("${pageContext.request.contextPath }/resources/roleEmpowerments", "#treeDemo"); //创建
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
    
    var rshow = document.getElementById('Empowerment_Show');
    var rohide = document.getElementById('Empowerment_Over');
    // 显值赋权
    function role_show(id){
        $.ajax({
            type: "get",  // 请求方式(post或get)
            async:false,  //默认true(异步请求),设置为false(同步请求)
            url:"${pageContext.request.contextPath }/resources/roleEmpowerment", // 发送请求的地址
            scriptCharset: 'utf-8',
            dataType:"json",
            data: {"id" : id},
            success:function(resource){
                $("#ddiv").append("<input type='hidden' value='"+id+"' id='id1' name='id1'>");
                zTree = $.fn.zTree.getZTreeObj("treeDemo"); // ztree树的ID
                /** 获取所有树节点 */
                var nodes = zTree.transformToArray(zTree.getNodes());
                // 遍历树节点设置树节点为未选中
                for (var k = 0, length_3 = nodes.length; k < length_3; k++) {
                    nodes[k].checked = false;
                    zTree.updateNode(nodes[k],true);
                }
                // 选中勾选权限
                for(var i = 0;i < resource.length;i++)//返回用户的权限
                {
                    zTree.checkNode( zTree.getNodeByParam( "id",resource[i]), true ); //根据id在ztree的复选框中实现自动勾选
                }
            }
        });
        
        rshow.style.display = "block";
        rohide.style.display = "block";
    }
    // 隐藏权限弹出层
    function role_hide()
    {  
        rshow.style.display = "none";
        rohide.style.display = "none";
    }
    </script>
</html>