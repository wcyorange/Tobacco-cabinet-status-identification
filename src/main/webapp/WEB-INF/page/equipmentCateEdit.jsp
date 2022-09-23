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
                    <label class="layui-form-label">柜号</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" lay-verify="required" autocomplete="off" placeholder="请输入柜号" value="${equipmentCate.name}"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">摄像头状态</label>
                    <div class="layui-input-inline">
                        <select name="status" lay-verify="required" lay-search="" id="status">
                            <option value="">直接选择摄像头状态</option>
                            <option value="1">1-运行中</option>
                            <option value="2">2-停止运行</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">删除状态</label>
                    <div class="layui-input-inline">
                        <select name="deleteStatus" lay-verify="required" lay-search="" id="deleteStatus">
                            <option value="">直接选择删除状态</option>
                            <option value="0">0-未删除</option>
                            <option value="1">1-删除</option>
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
<script src="../layuiadmin/layui/layer.js"></script>
<script>
    layui.use('form', function(){
        var form = layui.form;
        $ = layui.$;

        $("#status").val("${equipmentCate.status}");
        $("#deleteStatus").val("${equipmentCate.deleteStatus}");
        form.render('select','selFilter');
    });
</script>
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
            $.post("<%=request.getContextPath()%>/equipmentCate/edit", data.field, function (data) {
                // 获取 session
                if (data.status == "success") {layer.alert("用户信息修改成功", {icon: 6}, function () {
                    location.reload();
                    parent.layui.admin.events.closeThisTabs();
                });
                }else{layer.alert("用户信息修改失败", {icon: 6}, function () {
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
                var url = "equipmentCate/listjson";//get
                $.ajax({
                    type:"post",
                    url: url,
                    async: false,
                    data:res,
                    dataType:'json',
                    success: function(data) {
                        if(data.status =="success"){
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
