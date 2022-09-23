<%--
  Created by IntelliJ IDEA.
  User: 李岩
  Date: 2019/8/10
  Time: 12:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>合伙人信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-header">合伙人信息详情</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" action="" lay-filter="component-form-group">
                <div class="layui-form-item">
                    <label class="layui-form-label">微信头像：</label>
                    <div class="layui-input-inline">
                        <%--<input type="text" disabled="true" name="agentName" lay-verify="required" autocomplete="off" class="layui-input" value="${flatformAgent.agentName}">--%>
                    <div>
                        <c:if test="${flatformPartner.avatarUrl!=null}">
                            <img  style="width: 30%; height:30%;border-radius: 50% "  onclick="previewImg(this)" src="layuiadmin/touxiang/${flatformPartner.avatarUrl}">
                        </c:if>

                    </div>

                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">微信昵称：</label>
                        <div class="layui-input-inline">
                            <input type="text" disabled="true" name="tinyName" lay-verify="required" autocomplete="off" class="layui-input" value="${flatformPartner.tinyName}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label" style="width: 90px">合伙人名称：</label>
                        <div class="layui-input-inline">
                            <input type="text" disabled="true" name="parName" lay-verify="required" autocomplete="off" class="layui-input" value="${flatformPartner.parName}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">性别:</label>
                        <div class="layui-input-inline">
                            <input type="text" disabled="true" name="sex" lay-verify="required" autocomplete="off" class="layui-input" value="${flatformPartner.sex}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">联系电话：</label>
                        <div class="layui-input-inline">
                            <input type="text" disabled="true" name="mobile" lay-verify="required" autocomplete="off" class="layui-input" value="${flatformPartner.mobile}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label"style="width: 90px">身份证号码：</label>
                        <div class="layui-input-inline">
                            <input type="text" disabled="true" name="identity" lay-verify="required" autocomplete="off" class="layui-input" value="${flatformPartner.identity}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">持卡人姓名：</label>
                        <div class="layui-input-inline">
                            <input type="text" disabled="true" name="cardname" lay-verify="required" autocomplete="off" class="layui-input" value="${flatformPartner.cardname}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">银行卡号：</label>
                        <div class="layui-input-inline">
                            <input type="text" disabled="true" name="cardnumber" lay-verify="required" autocomplete="off" class="layui-input" value="${flatformPartner.cardnumber}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">开户银行：</label>
                        <div class="layui-input-inline">
                            <input type="text" disabled="true" name="cardbank" lay-verify="required" autocomplete="off" class="layui-input" value="${flatformPartner.cardbank}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">经纪人：</label>
                        <div class="layui-input-inline">
                            <input type="text" disabled="true" name="uName" lay-verify="required" autocomplete="off" class="layui-input" value="${uName}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label"style="width: 90px">上级合伙人：</label>
                        <div class="layui-input-inline">
                            <input type="text" disabled="true" name="parName" lay-verify="required" autocomplete="off" class="layui-input" value="${parName}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">创建时间：</label>
                        <div class="layui-input-inline">
                            <input type="text" disabled="true" name="createTime" lay-verify="required" autocomplete="off" class="layui-input" value="${flatformPartner.createTime}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">审核状态：</label>
                        <div class="layui-input-inline">
                            <div style="margin-top: 8px">
                            <c:if test="${flatformPartner.status==0}">
                                <span style="color: #FF4500;font-size: 18px ">待审核</span>
                            </c:if>
                               <c:if test="${flatformPartner.status == 1}">
                                   <span style="color: green; font-size: 18px">审核通过</span>
                               </c:if>
                               <c:if test="${flatformPartner.status =='-1'}">
                                   <span style="color: #FF0000; font-size: 18px">审核驳回</span>
                               </c:if>
                                <c:if test="${flatformPartner.status==2}">
                                    <span style="color: #FF4500;font-size: 18px ">信息待完善</span>
                                </c:if>
                                <c:if test="${flatformPartner.status==3}">
                                    <span style="color: green;font-size: 18px ">签订成功</span>
                                </c:if>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: auto;height: auto;">合伙人详细地址</label>
                    <div class="layui-input-block">
                        <input type="text" lay-verify="required" disabled="disabled" value="${flatformPartner.address}" name="address" placeholder="请输入实际经营详细地址"
                               autocomplete="off" class="layui-input" style="width: 90%;">
                    </div>
                </div>

                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                    <legend>图片信息</legend>
                </fieldset>

                <div class="layui-inline">
                    <label class="layui-form-label" style="width: auto;height: auto;">身份证正面照片</label>
                    <div class="upImg">
                        <div class="layui-upload-drag" style="padding: 0px">
                            <c:if test="${flatformPartner.imgUrlFont !=null}">
                                <img class="layui-upload-img headImage" alt="未上传！"  onclick="previewImg(this)" style="width: 258px;height: 124px;color: red;" src="layuiadmin/touxiang/${flatformPartner.imgUrlFont}">
                            </c:if>

                        </div>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label" style="width: auto;height: auto;">身份证反面照片</label>
                    <div class="upImg" style="margin-left: 10px">
                        <div class="layui-upload-drag" style="padding: 0px">
                            <c:if test="${flatformPartner.imgUrlBack !=null}">
                            <img class="layui-upload-img headImage" alt="未上传！" onclick="previewImg(this)" style="width: 258px;height: 124px;color: red;" src="layuiadmin/touxiang/${flatformPartner.imgUrlBack}">
                            </c:if>
                        </div>
                    </div>
                </div>




                <div class="layui-form-item layui-layout-admin">
                    <div class="layui-input-block">
                        <div class="layui-footer" style="left: 0;">
                            <button class="layui-btn" lay-submit="" lay-filter="component-form-demo">关闭</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


<script src="layuiadmin/layui/layui.js"></script>
</script><script src="layuiadmin/jquery.min.js"></script>

<script>

    function onImgError(dom){
        var $dom=$(dom);//转转成Jquery对象
//  $dom.hide();
        $dom.remove();//没有任何反应,为何？
        dom.onerror=null;
    }
    function previewImg(obj) {
        var img = new Image();
        img.src = obj.src;
        var imgHtml = "<img src='" + obj.src + "' width='100%' height='100%'/>";
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
<script type="text/javascript">

</script>
<script>
    layui.config({
        base: 'layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'form', 'laydate'], function(){
        var $ = layui.$
            ,admin = layui.admin
            ,element = layui.element
            ,layer = layui.layer
            ,laydate = layui.laydate
            ,form = layui.form;

        form.render(null, 'component-form-group');

        laydate.render({
            elem: '#LAY-component-form-group-date'
        });


        /* 监听提交 */
        form.on('submit(component-form-demo)', function(data){

            parent.layui.admin.events.closeThisTabs();

            return false;
        });
    });
</script>

</body>
</html>

