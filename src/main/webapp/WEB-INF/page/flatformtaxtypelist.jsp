<%--
  Created by IntelliJ IDEA.
  User: 昭昭
  Date: 2019/12/14
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <title>分润配置</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="layuiadmin/style/admin.css" media="all">
</head>
<style>
    .layui-table-cell{
        height:50px;
        line-height: 50px;
    }
</style>

<body>
<div class="layui-card layadmin-header" style="display:block;">
    <div class="layui-breadcrumb" lay-filter="breadcrumb">
        <a lay-href="">主页</a>
        <a><cite>分润配置</cite></a>
        <a><cite>分润配置列表</cite></a>
    </div>
</div>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">
            </div>
        </div>
        <div class="layui-row" id="test" style="display: none;">
            <div class="layui-col-md10" style="margin-top:30px">
                <form class="layui-form" id="endit" lay-filter="example">
                </form>
            </div>
        </div>
        <div class="layui-card">
            <div class="layui-card-body">
                <table class="layui-hide" id="test-table-toolbar" lay-filter="test-table-toolbar">

                </table>
                <script type="text/html" id="test-table-toolbar-toolbarDemo">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-sm" lay-event="add">添加分润配置</button>
                    </div>
                </script>

                <script type="text/html" id="test-table-toolbar-barDemo">
                    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑配置信息</a>
                </script>
                <div id="pagination" class="layui-table-page" style="padding:14px 0px 0px;text-align:right;"></div>
            </div>
        </div>
    </div>
</div>
<script src="layuiadmin/layui/layui.js"></script>
<script src="layuiadmin/jquery.min.js"></script>
<script>
    function onImgError(dom){
        var $dom=$(dom);//转转成Jquery对象
//  $dom.hide();
        $dom.remove();//没有任何反应,为何？
        dom.onerror=null;
    }
</script>
<script>
    layui.config({
        base: 'layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index','form','table'], function() {
        var admin = layui.admin
            , table = layui.table;
        var flag = 0;
        table.render({
            elem: '#test-table-toolbar'
            , url: 'source/listjson' //修改这个地址，列表查询路径，数据类型见json/table/demo.js
            , toolbar: '#test-table-toolbar-toolbarDemo'
            , title: '菜单数据表'
            , cols: [[
                {field:'ttId', title: '序号', align: 'center', fixed: 'left'}
                , {field: 'ttName', title: '资源名称', align: 'center', fixed: 'left'}
                , {field: 'tType', title: '资源描述', align: 'center', fixed: 'left'}
                , {field: 'num', title: '联系电话', align: 'center', fixed: 'left', width: 120}
                , {field: 'tax', title: '资源地址', align: 'center', templet: '#sexTpl'}
                , {field: 'pTax', title: '星级', align: 'center'}
                , {field: 'money', title: '金额', align: 'center'}
                , {fixed: 'right', title: '操作', align: 'center', toolbar: '#test-table-toolbar-barDemo', width: 330}
            ]]
            , even: true
            , done: function (res, curr, count) {
            }
        });
        table.on('toolbar(test-table-toolbar)', function(obj){
            if(obj.event=="add"){
                parent.layui.index.openTabsPage("source/add", "添加用户");
            }
        });
        table.on('tool(test-table-toolbar)', function(obj){
            var user=obj.data;
            if(obj.event === 'del'){
                var data = {};
                data['id'] = user.sId
                layer.confirm('真的删除行么', function(index){
                    admin.req({
                        type: "POST"
                        ,url: 'source/delete' //实际使用请改成服务端真实接口
                        ,data: data
                        ,done: function(res){
                            if(res.status=="succ"){
                                obj.del();
                                table.reload('test-table-toolbar', {});
                                layer.close(index);
                            }
                        }
                    })
                });
            }else if(obj.event === 'edit'){
                parent.layui.index.openTabsPage("source/edit?id="+obj.data.sId, "修改资源");
            }
        })
    })
</script>
</body>
</html>
