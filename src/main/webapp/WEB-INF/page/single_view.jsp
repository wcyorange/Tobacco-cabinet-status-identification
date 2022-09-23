<%--
  Created by IntelliJ IDEA.
  User: 宁志洋
  Date: 2020/10/21
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layuiadmin/layui/css/layui.css" media="all">
    <style type="text/css">
        .a {
            margin-top: 3%;
            margin-left: 36%;
        }

        #cate{
            background: #b7d2be;
            height:35px;
            width:180px;
            line-height:28px;
            border:2px solid #d7e2ea;
            -moz-border-radius:4px;
            -webkit-border-radius:4px;
            border-radius:4px;
        }

        #e_name{
            background: #bfd4b5;
            height:35px;
            width:180px;
            line-height:28px;
            border:2px solid #dae4ec;
            -moz-border-radius:4px;
            -webkit-border-radius:4px;
            border-radius:4px;
        }
    </style>
</head>
<body>
<div class="a">

    <label for="cate"></label>
    <select id="cate">
        <option value="0">选择查看的机柜组</option>
        <c:forEach items="${thinkEquipmentCates}" var="thinkEquipmentCates">
            <option value="${thinkEquipmentCates.id}"
                    id="${thinkEquipmentCates.id}">${thinkEquipmentCates.name}</option>
        </c:forEach>
    </select>

    <label for="e_name"></label>
    <select id="e_name">
        <option value="0">选择查看的机柜</option>
    </select>

    <button id="findEq" class="layui-btn layui-btn-radius"><i class="layui-icon layui-icon-search"></i> Find
    </button>

</div>
<div style="position: relative">
    <video style="height: 78%;width: 83%;position:relative;margin-left: 10%;margin-top: 5%" id="videoElement"
           controls
           autoplay></video>
</div>

<script src="https://cdn.bootcdn.net/ajax/libs/flv.js/1.5.0/flv.min.js"></script>
<script src="layuiadmin/js/jquery-3.4.1.min.js"></script>

<script type="text/javascript">
    $("#cate").on("change", function () {
        $('findEq').attr('disabled', true);
        $('#e_name').empty();
        $('#e_name').append('<option value="0">选择查看的机柜</option>');
        var id = $(this).find("option:selected").val();
        $.post("single_view/findName", {cate: id}, function (data) {
            $.each(data.result, function (index, item) {
                $('#e_name').append("<option value='" + item.code + "'>" + item.code + "</option>");
            });
        });
    });


    $("#findEq").on("click", function () {
        var code = $('#e_name').find("option:selected").val();
        $.post("gainRtmp", {code: code}, function (data) {

            var flvPlayer = null;
            if (flvjs.isSupported()) {
                var videoElement = document.getElementById('videoElement');
                flvPlayer = flvjs.createPlayer({
                    type: 'flv',
                    "islive": true,
                    // 8080 对应Nginx监听的端口
                    // rtmpLive 对应Nginx的路径
                    //localhost:1935/live/room
                    url: data.live_url
                });
                flvPlayer.attachMediaElement(videoElement);
                flvPlayer.load();
            }
        });
    });

</script>
</body>
</html>
