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
    <title>菜单列表</title>
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
        <a><cite>消费管理</cite></a>
        <a><cite>签订合伙人列表</cite></a>
    </div>
</div>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-input-inline">
                        <input type="text" name="parName"  placeholder="请输入合伙人名称" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <div class="layui-input-inline">
                        <input type="text" name="mobile"  placeholder="请输入手机号" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn layuiadmin-btn-comm" data-type="reload" lay-submit lay-filter="LAY-js-search">
                        <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="layui-row" id="test" style="display: none;">
            <div class="layui-col-md10" style="margin-top:30px">
                <form class="layui-form" id="endit" lay-filter="example">
                    <div class="layui-form-item">
                        <label class="layui-form-label" >联系电话:</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input" onkeyup="value=zhzs(this.value)" lay-verify="required|number" name="mobile" placeholder="合伙人手机号">
                        </div>
                    </div>

                </form>
            </div>
        </div>





        <div class="layui-card">
            <div class="layui-card-body">
                <table class="layui-hide" id="test-table-toolbar" lay-filter="test-table-toolbar">

                </table>


                <script type="text/html" id="test-table-toolbar-barDemo">


                    <a class="layui-btn layui-btn-xs" lay-event="view">查看消费信息</a>
                </script>
                <div id="pagination" class="layui-table-page" style="padding:14px 0px 0px;text-align:right;"></div>
            </div>
        </div>
    </div>
</div>
<script src="layuiadmin/layui/layui.js"></script>
<script src="layuiadmin/jquery.min.js"></script>
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
<script type="text/html" id="sexTpl">
    {{# if(d.status == 0){ }}
    <span style="color: #FF4500; ">待审核</span>
    {{# } else if(d.status ==1) { }}
    <span style="color: green;">审核通过</span>
    {{#} else if(d.status ==-1){ }}
    <span style="color: #FF0000; ">审核驳回</span>
    {{# } else if(d.status ==2) { }}
    <span style="color: #FF4500;">信息待完善</span>
    {{# } else if(d.status ==3) { }}
    <span style="color: green;">签订成功</span>
    {{# } }}
</script>
<script type="text/html" id="sexTp2">
    {{# if(d.dealNumbers != 0){ }}
    <span style="color: #FF4500; ">{{d.dealNumbers}}</span>
    {{# } else if(d.dealNumbers ==0) { }}
    <span>{{d.dealNumbers}}</span>
    {{# } }}
</script>
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
    }).use(['index','form','table'], function(){
        var admin = layui.admin
            ,table = layui.table;
        var flag=0;
        table.render({
            elem: '#test-table-toolbar'
            ,url: 'order/listjson1' //修改这个地址，列表查询路径，数据类型见json/table/demo.js
            ,toolbar: '#test-table-toolbar-toolbarDemo'
            ,title: '菜单数据表'
            ,cols: [[
                {type:'numbers', title:'序号', align:'center', fixed: 'left'}
                ,{field:'avatarUrl', title:'微信头像', align:'center', fixed: 'left', width:90,templet: '<div><img id="imgs" style="width: 100%; height:100%;border-radius: 50% " onerror="onImgError(this);" onclick="previewImg(this)" src="layuiadmin/touxiang/{{d.avatarUrl}}"></div>'}
                ,{field:'tinyName', title:'微信昵称', align:'center', fixed: 'left'}
                ,{field:'parName', title:'合伙人名称', align:'center', fixed: 'left'}
                ,{field:'mobile', title:'联系电话', align:'center', fixed: 'left', width:120}
                ,{field:'status', title:'状态', align:'center', templet: '#sexTpl'}
                ,{field:'dealNumbers', title:'消费单数', align:'center', templet: '#sexTp2'}
                ,{fixed: 'right', title:'操作', align:'center', toolbar: '#test-table-toolbar-barDemo'}
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
                layer.open({
                    title:"一级合伙人账号开通"
                    ,type:1
                    ,moveOut:true
                    ,content:$("#test")
                    ,offset:['20%','30%']
                    ,area:['400px','300px']
                    // ,success: function(layero, index){},
                    ,btn:['提交','取消'],
                    yes:function(index,layero){
                        //发送请求
                        var d={};
                        var res = layero.find('input')[0].value;
                        d['mobile']=res;
                        $.post("partner/addPartner",d,function(data){
                            // 获取 session
                            if(data.status!=200){
                                layer.alert("开通失败，请确定该账号是否已被注册！");
                            }
                            if(data.status==200){
                                layer.msg("开通成功", {
                                    /* window.location.reload();*/
                                    icon: 6,
                                    time: 1500
                                }, function () {
                                    window.location.reload();
                                });

                            }
                            layer.close(index); //关闭弹窗


                        });
                        //var index = parent.layer.getFrameIndex(window.name);
                        // window.location.reload();
                    }

                });
                form.val('example',data);
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
                ,url: '/pay/updateHandle' //实际使用请改成服务端真实接口
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
            var menu=obj.data;
            if(obj.event === 'del'){
                var data = {};
                data['parId'] = menu.parId
                layer.confirm('真的删除行么', function(index){
                    admin.req({
                        type: "POST"
                        ,url: 'partner/delete' //实际使用请改成服务端真实接口
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
            }else if(obj.event === 'pass'){
                var data = {};
                data['parId'] = menu.parId;
                data['status']=1;
                layer.confirm('确定审核通过吗?', function(index){
                    $.post("partner/update",data,function(data){
                        // 获取 session
                        if(data.status=="error" ){
                            layer.msg("操作失败", {
                                /* window.location.reload();*/
                                icon: 5,
                                time: 1500
                            }, function () {
                                window.location.reload();
                            });
                        }

                        if(data.status=="succ"){
                            layer.msg("操作成功", {
                                /* window.location.reload();*/
                                icon:6,
                                time:1500
                            },function(){window.location.reload();});
                        }
                    });
                });
            }else if(obj.event === 'reject'){
                var data = {};
                data['parId'] = menu.parId;
                data['status']=-1;
                layer.confirm('确定审核驳回吗?', function(index){
                    $.post("partner/update",data,function(data){
                        // 获取 session
                        if(data.status=="error" ){
                            layer.msg("操作失败", {
                                /* window.location.reload();*/
                                icon: 5,
                                time: 1500
                            }, function () {
                                window.location.reload();
                            });
                        }

                        if(data.status=="succ"){
                            layer.msg("操作成功", {
                                /* window.location.reload();*/
                                icon:6,
                                time:1500
                            },function(){window.location.reload();});
                        }
                    });
                });
            }else if(obj.event === 'SignIn'){
                var data = {};
                data['parId'] = menu.parId;
                data['status']=3;
                layer.confirm('确定签订入网吗?', function(index){
                    $.post("partner/signIn",data,function(data){
                        // 获取 session
                        if(data.status=="error" ){
                            layer.msg("操作失败,信息审核未通过或请联系管理员", {
                                /* window.location.reload();*/
                                icon: 5,
                                time: 1500
                            }, function () {
                                window.location.reload();
                            });
                        }

                        if(data.status=="succ"){
                            layer.msg("操作成功", {
                                /* window.location.reload();*/
                                icon:6,
                                time:1500
                            },function(){window.location.reload();});
                        }
                    });
                });
            }else if(obj.event === 'view'){
                parent.layui.index.openTabsPage("costflow/viewlist?parId="+menu.parId, "查看信息");
            }
        });
    });
</script>
</body>
</html>
