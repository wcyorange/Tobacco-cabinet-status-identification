<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="../layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-header">修改用户</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" action="" lay-filter="component-form-group">
                <input type="hidden" value="${flatformAccount.uLoginName}" id="loginname" >
                <input type="hidden" value="${flatformAccount.uId}" name="uId"/>
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block">
                        <input type="text" name="uName" lay-verify="title" autocomplete="off" placeholder="请输入用户名" value="${flatformAccount.uName}" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">邮箱</label>
                    <div class="layui-input-block">
                        <input type="text" name="email" lay-verify="email" autocomplete="off" placeholder="请输入邮箱" value="${flatformAccount.email}" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">

                    <div class="layui-inline">
                        <label class="layui-form-label">权限组ID</label>
                        <div class="layui-input-inline">
                            <select name="agId" lay-verify="required" lay-search="">
                                <c:forEach items="${flatformAdminGroups}" var="flatformAdminGroups">
                                    <c:choose>
                                        <c:when test="${flatformAccount.agId==flatformAdminGroups.agId}">
                                            <option value=${flatformAdminGroups.agId } selected="selected">${flatformAdminGroups.agName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value=${flatformAdminGroups.agId }>${flatformAdminGroups.agName }</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">用户级别</label>
                            <div class="layui-input-inline">
                                <select name="level" lay-verify="required" lay-search="">
                                    <option value="4">默认</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">登录名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="uLoginName" lay-verify="usercheck" autocomplete="off" placeholder="请输入登录名称" value="${flatformAccount.uLoginName}" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">登录密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="uLoginPwd" id="password" lay-verify="pass" autocomplete="off" placeholder="请输入密码"  class="layui-input"  >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">确认密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="" id="password1" lay-verify="pass1" autocomplete="off" placeholder="请再次输入密码"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">验证手机</label>
                        <div class="layui-input-inline">
                            <input type="tel" name="mobile" lay-verify="required|phone" placeholder="请输入手机号" value="${flatformAccount.mobile}" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
<%--                <div class="layui-form-item">--%>
<%--                    <label class="layui-form-label">用户状态</label>--%>
<%--                    <div class="layui-input-block">--%>
<%--                        <input type="checkbox" name="status" id="status" <c:if test="${flatformAccount.status==0}"> checked</c:if> lay-skin="switch" lay-text="开启|关闭"  >--%>
<%--                    </div>--%>
<%--                </div>--%>


                <div class="layui-form-item layui-layout-admin">
                    <div class="layui-input-block">
                        <div class="layui-footer" style="left: 0;">
                            <button class="layui-btn" lay-submit="" lay-filter="component-form-demo">立即提交</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        </div>
    </div>
</div>


<script src="../layuiadmin/layui/layui.js"></script>
<script src="../layuiadmin/jquery.min.js"></script>
<script>
    layui.config({
        base: '../layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'form', 'laydate'], function () {
        var $ = layui.$
            , admin = layui.admin
            , element = layui.element
            , layer = layui.layer
            , laydate = layui.laydate
            , form = layui.form;

        form.render(null, 'component-form-group');

        laydate.render({
            elem: '#LAY-component-form-group-date'
        });

        /* 自定义验证规则 */
        form.verify({
            title: function (value) {
                if (value.length < 1) {
                    return '请输入必填内容';
                }
            }
            , pass: [/(.+){6,12}$/, '密码必须6到12位']
            , content: function (value) {
                layedit.sync(editIndex);
            }
            ,pass1: function(value){
                if(value!=$("#password").val()){
                    return '两次密码输入不同,请重新输入';
                }
            }
            ,usercheck:function (value,item){
                if(value!=$("#loginname").val()) {
                    var msg;
                    var data = {};
                    data['account'] = value;
                    $.ajax({
                        type: "post",
                        url: "check",
                        async: false,
                        data: data,
                        dataType: 'json',
                        success: function (data) {
                            if (data) {
                                msg = "该账号已存在";
                            }
                        },
                        error: function () {
                        }
                    });
                    if (value == '')
                        msg = "账号不能为空"
                    return msg;
                }
          }

        });
        /* 监听提交 */
        form.on('submit(component-form-demo)', function (data) {
            $.post("update", data.field, function (data) {
                // 获取 session
                if (data.status == "error") {
                    layer.alert("您没有更新权限，请联系管理员！");
                }
                if (data.status == "succ") {
                    layer.alert("更新成功", {icon: 6}, function () {
                        location.reload();
                        parent.layui.admin.events.closeThisTabs();
                    });
                }
            });
            return false;
        });


    });
</script>
</body>
</html>
