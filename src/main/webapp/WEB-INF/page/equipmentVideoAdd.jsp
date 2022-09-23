<%--
  Created by IntelliJ IDEA.
  User: 丁宁
  Date: 2019/8/7
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>内容管理</title>
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
        <div class="layui-card-header">平台功能模块</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" action="" lay-filter="component-form-group">

                <div class="layui-form-item">
                    <label class="layui-form-label">设备ID</label>
                    <div class="layui-input-block">
                        <input type="text" name="eqId" lay-verify="required" autocomplete="off" placeholder="请输入设备ID"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">视频保存地址</label>
                    <div class="layui-input-block">
                        <input type="text" name="videoUrl" lay-verify="required" autocomplete="off" placeholder="请输入视频保存地址"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">事故存储批次 时间戳</label>
                    <div class="layui-input-block">
                        <input type="text" name="unionId" lay-verify="required" autocomplete="off" placeholder="请输入事故存储批次 时间戳"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">视频录制开始时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="beginTime" lay-verify="required" autocomplete="off" placeholder="请输入视频录制开始时间"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">视频录制结束时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="endTime" lay-verify="required" autocomplete="off" placeholder="请输入视频录制结束时间"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">机械状态</label>
                    <div class="layui-input-inline">
                        <select name="status" lay-verify="required" lay-search="" id="status">
                            <option value="">直接选择机械状态</option>
                            <option value="0">0-正常</option>
                            <option value="1">1-异物状态</option>
                            <option value="2">2-空料状态</option>
                            <option value="3">3-满料状态</option>
                            <option value="4">4-送料状态</option>
                            <option value="5">5-加料状态</option>
                        </select>
                    </div>
                </div>
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


<script src="../layuiadmin/layui/layui.js"></script>
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
            elem: '#LAY-component-form-group-date1'
            , type: 'datetime'
        });
        laydate.render({
            elem: '#LAY-component-form-group-date2'
            , type: 'datetime'
        });

        /* 自定义验证规则 */
        form.verify({
        });

        /* 监听提交 */
        var form =layui.form;
        form.on('submit(component-form-demo)', function (data) {
            $.post("/camera/equipmentVideo/insert", data.field, function (data) {
                // 获取 session
                if (data.status == "succ") {
                    parent.layui.admin.events.closeThisTabs();
                }else{("新增失败", {icon: 6}, function () {
                        parent.layui.admin.events.closeThisTabs();
                    });
                }
            });
            return false;
        });
        form.on('select(test)', function(data){
            if (data.value>1){
                $("#id").empty();
                var res = {};
                res['value'] = data.value -1;
                var url = "equipmentVideo/listjson";//get
                $.ajax({
                    type:"post",
                    url: url,
                    async: false,
                    data:res,
                    dataType:'json',
                    success: function(data) {
                        if(data.status =="succ"){
                            $('#id').append(new Option("操作成功","0"));
                            form.render();//菜单渲染 把内容加载进去
                        }else{
                            $('#id').append(new Option("数据异常","0"));
                        }

                    },
                    error: function() {
                    }
                });
            }else{
                $("#au_pid").empty();
                $('#au_pid').append(new Option("无上级菜单","0"));
                form.render();//菜单渲染 把内容加载进去
            }

        });
    });
</script>
</body>
</html>

