<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <base href="<%=basePath %>" />
    <title>职位列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layuiadmin/style/admin.css" media="all">
</head>
<body>
<div class="layui-card layadmin-header" style="display:block;">
    <div class="layui-breadcrumb" lay-filter="breadcrumb">
        <a lay-href="">主页</a>
        <a><cite>功能管理</cite></a>
        <a><cite>配置默认</cite></a>
    </div>
</div>
<div class="layui-fluid">
        <div class="layui-card">
            <div class="layui-card-body">
                <table class="layui-hide" id="test-table-toolbar" lay-filter="test-table-toolbar">

                </table>
                <script type="text/html" id="test-table-toolbar-toolbarDemo">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-sm" lay-event="add">添加参数</button>
                    </div>
                </script>
                <script type="text/html" id="test-table-toolbar-barDemo">
                    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                    {{# if(d.id === 106){ }}
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="detail">查看</a>
                    {{# } }}
                </script>
                <div id="pagination" class="layui-table-page" style="padding:14px 0px 0px;text-align:right;"></div>
            </div>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/layuiadmin/layui/layui.js"></script>

<script>
    function previewImg(obj) {
        var img = new Image();
        img.src = obj.src;
        var imgHtml = "<img  src='" + obj.src + "' width='100%' height='100%' />";
        //弹出层
        layer.open({
            type: 1,
            shade: 0.8,
            offset: 'auto',
            area: [50 + '%',50+'%'],
            shadeClose:true,
            scrollbar: false,
            title: "图片预览", //不显示标题
            content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function () {
                //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });
            }
        });
    }
</script>

<script>
    layui.config({
        base: 'layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index','form','table'], function(){
        var admin = layui.admin
            ,table = layui.table;
        var flag=0;
        table.render({
            elem: '#test-table-toolbar'
            ,url: 'cg/listjson' //修改这个地址，列表查询路径，数据类型见json/table/demo.js
            ,toolbar: '#test-table-toolbar-toolbarDemo'
            ,title: '数据表'
            ,cols: [[
                {field:'id', title:'分组ID', align:'center',sort:true,fixed: 'left',width: 120}
                ,{field:'groupName', title:'组名', align:'center',width: 220}
                ,{field:'eqId', title:'设备编号', align:'center',width: 300}
                ,{field:'uName', title:'创建者', align:'center',width: 120}
                ,{field:'deleteStatus', title:'删除标记', align:'center',width: 120,templet:function(d){
                        if(d.deleteStatus ==0)
                            return '未删除';
                        else
                            return '<span style="color: red">已删除</span>';
                    }}
                ,{field:'deleteTime', title:'删除时间', align:'center',width: 220}
                ,{fixed:'right', title:'操作', align:'center',  toolbar: '#test-table-toolbar-barDemo'}
            ]]
            ,even: true
            ,done: function(res, curr, count){
                if(flag==0){
                    loadPagination(count);
                    flag=1;
                }
            }
        });
        //点击头部按钮操作
        table.on('toolbar(test-table-toolbar)', function(obj){
            if(obj.event=="add"){
                parent.layui.index.openTabsPage("cg/addForm", "添加参数");
            }
        });

        table.on('edit(test-table-toolbar)', function(obj){
            //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data={};
            data['field']=obj.field;//当前编辑的字段名
            data['value']=obj.value;//得到修改后的值
            data['id']=obj.data.id;//所在行的所有相关数据
            admin.req({
                type: "POST"
                ,url: '/camera/updateHandle' //实际使用请改成服务端真实接口
                ,data: data
                ,done: function(res){
                    layer.tips('信息修改成功', this, {
                        tips: [3, '#2F9688'],
                        time: 1000
                    });
                }
            });
        });
        //这段不需要修改
        function loadPagination(count){
            var laypage = layui.laypage;
            laypage.render({
                elem: 'pagination'
                ,count: count //数据总数，从服务端得到
                ,layout: ['count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                // 限定条数   总数、计数  上一页     页     下一页    到第几页、跳
                ,curr: 1
                ,groups:5//显示 连续页码
                ,first: '首页'
                ,last: '尾页'
                ,jump: function(obj, first){
                    //obj包含了当前分页的所有参数，比如：
                    console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                    console.log(obj.limit); //得到每页显示的条数
                    if(!first){
                        table.reload('test-table-toolbar', {
                            where: { //设定异步数据接口的额外参数，任意设
                                page:obj.curr
                            }
                        });
                    }
                }
            });
        }

        //监听搜索
        var form = layui.form;
        form.on('submit(LAY-js-search)', function(data){
            var fileds = data.field;
            fileds['page']=1;
            //执行重载
            flag=0;
            table.reload('test-table-toolbar', {
                where: fileds
            });
        });
        //监听行工具事件
        table.on('tool(test-table-toolbar)', function(obj){
            var p=obj.data;
            if(obj.event === 'del'){
                var data = {};
                data['id'] = p.id;
                layer.confirm('真的删除行么', function(index){
                    admin.req({
                        type: "POST"
                        ,url: 'cg/delete' //实际使用请改成服务端真实接口
                        ,data: data
                        ,done: function(res){
                            if(res){
                                obj.del();
                                table.reload('test-table-toolbar', {});
                                layer.close(index);
                            }
                        }
                    })
                });
            }else if(obj.event === 'edit'){
                parent.layui.index.openTabsPage("cg/updateForm?id="+obj.data.id, "修改参数");
            }

        });
    });
</script>
</body>
</html>
